package com.plussub.opensubtitle.proxy.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import com.plussub.opensubtitle.proxy.OpenSubtitleAction;
import com.plussub.opensubtitle.proxy.login.LoginToken;
import com.plussub.opensubtitle.proxy.rpc.RpcClient;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

/**
 * Created by sonste on 27.02.2017.
 */
public class SearchActionTest {

    @Bind
    private RpcClient rpcClientMock;

    @Inject
    private SearchAction searchAction;

    private final static LoginToken LOGIN_TOKEN = new LoginToken("aToken");
    private final static String IMDB_ID = "0123";
    private final static String ISO_LANGUAGE_CODE = "eng";


    @Test(expected = SearchException.class)
    public void expect_failed_rpc_call() throws Exception {

        IMocksControl mocksControl = EasyMock.createControl();
        rpcClientMock = mocksControl.createMock(RpcClient.class);

        expectOpenSubtitleRpcQuery(expectFailedRpcResult());

        List<?> result = replayAndVerify(mocksControl);

        assertThat(result, is(empty()));
    }

    @Test
    public void expect_empty_result() throws Exception {

        IMocksControl mocksControl = EasyMock.createControl();
        rpcClientMock = mocksControl.createMock(RpcClient.class);

        expectOpenSubtitleRpcQuery(expectRpcResult(Collections.emptyList()));

        List<?> result = replayAndVerify(mocksControl);

        assertThat(result, is(empty()));
    }

    @Test
    public void should_only_accept_srt_formats() throws Exception {

        final Map<String, String> srtEntry = createRpcSearchResultEntry("srt", "utf-8", 10.0);

        List<Map<String, String>> expectedSearchResultData = Lists.newArrayList();
        expectedSearchResultData.add(createRpcSearchResultEntry("txt", "utf-8", 10.0));
        expectedSearchResultData.add(srtEntry);
        expectedSearchResultData.add(createRpcSearchResultEntry("sub", "utf-8", 10.0));

        IMocksControl mocksControl = EasyMock.createControl();
        rpcClientMock = mocksControl.createMock(RpcClient.class);

        expectOpenSubtitleRpcQuery(expectRpcResult(expectedSearchResultData));

        List<Map<String, String>> result = replayAndVerify(mocksControl);

        assertThat(result.size(), is(1));
        assertThat(result, hasItem(srtEntry));

    }

    @Test
    public void should_only_accept_utf_8_and_ascii() throws Exception {

        final Map<String, String> utf8Entry = createRpcSearchResultEntry("srt", "utf-8", 10.0);
        final Map<String, String> asciiEntry = createRpcSearchResultEntry("srt", "utf-8", 10.0);


        List<Map<String, String>> expectedSearchResultData = Lists.newArrayList();
        expectedSearchResultData.add(createRpcSearchResultEntry("srt", "wtf-8", 10.0));
        expectedSearchResultData.add(utf8Entry);
        expectedSearchResultData.add(asciiEntry);
        expectedSearchResultData.add(createRpcSearchResultEntry("srt", "scp-8", 10.0));

        IMocksControl mocksControl = EasyMock.createControl();
        rpcClientMock = mocksControl.createMock(RpcClient.class);

        expectOpenSubtitleRpcQuery(expectRpcResult(expectedSearchResultData));

        List<Map<String, String>> result = replayAndVerify(mocksControl);

        assertThat(result.size(), is(2));
        assertThat(result, hasItem(utf8Entry));
        assertThat(result, hasItem(asciiEntry));


    }

    @Test
    public void result_should_sort_by_rating() throws Exception {


        List<Map<String, String>> expectedSearchResultData = Lists.newArrayList();
        expectedSearchResultData.add(createRpcSearchResultEntry("srt", "utf-8", 5.0));
        expectedSearchResultData.add(createRpcSearchResultEntry("srt", "utf-8", 1.0));
        expectedSearchResultData.add(createRpcSearchResultEntry("srt", "utf-8", 10.0));
        expectedSearchResultData.add(createRpcSearchResultEntry("srt", "utf-8", 7.0));

        List<Double> expectedSortedResultByRating = Lists.newArrayList(10.0,7.0,5.0,1.0);

        IMocksControl mocksControl = EasyMock.createControl();
        rpcClientMock = mocksControl.createMock(RpcClient.class);

        expectOpenSubtitleRpcQuery(expectRpcResult(expectedSearchResultData));

        List<Map<String, String>> result = replayAndVerify(mocksControl);


        List<Double> sortedResultByRating =  result.stream().map(m -> Double.valueOf(m.get("SubRating"))).collect(toList());

        assertThat(result.size(), is(4));
        assertThat(sortedResultByRating, is(expectedSortedResultByRating));

    }


    @Test
    public void mixed() throws Exception {

        final Map<String, String> utf8Entry = createRpcSearchResultEntry("srt", "utf-8", 5.0);
        final Map<String, String> asciiEntry = createRpcSearchResultEntry("srt", "ascii", 1.5);
        final Map<String, String> utf8OtherEntry = createRpcSearchResultEntry("srt", "utf-8", 10.0);
        final Map<String, String> utf8OtherEntry2 = createRpcSearchResultEntry("srt", "utf-8", 7.6);

        List<Map<String, String>> expectedSearchResultData = Lists.newArrayList();
        expectedSearchResultData.add(utf8Entry);
        expectedSearchResultData.add(asciiEntry);
        expectedSearchResultData.add(createRpcSearchResultEntry("sub", "ascii", 1.0));
        expectedSearchResultData.add(utf8OtherEntry);
        expectedSearchResultData.add(createRpcSearchResultEntry("srt", "wtf8", 7.5));
        expectedSearchResultData.add(utf8OtherEntry2);

        List<Double> expectedSortedResultByRating = Lists.newArrayList(10.0,7.6,5.0,1.5);


        IMocksControl mocksControl = EasyMock.createControl();
        rpcClientMock = mocksControl.createMock(RpcClient.class);

        expectOpenSubtitleRpcQuery(expectRpcResult(expectedSearchResultData));

        List<Map<String, String>> result = replayAndVerify(mocksControl);

        List<Double> sortedResultByRating =  result.stream().map(m -> Double.valueOf(m.get("SubRating"))).collect(toList());

        assertThat(result.size(), is(4));
        assertThat(result, hasItem(utf8Entry));
        assertThat(result, hasItem(asciiEntry));
        assertThat(result, hasItem(utf8OtherEntry));
        assertThat(result, hasItem(utf8OtherEntry2));
        assertThat(result, hasItem(utf8Entry));
        assertThat(sortedResultByRating, is(expectedSortedResultByRating));



    }

    private Map<String, String> createRpcSearchResultEntry(String format, String encoding, Double rating) {
        Map<String, String> entry = Maps.newHashMap();
        entry.put("SubFormat", format);
        entry.put("SubEncoding", encoding);
        entry.put("SubRating", rating.toString());
        return entry;
    }


    private Map<String, Object> expectFailedRpcResult() {
        Map<String, Object> expectedResultMap = Maps.newHashMap();
        expectedResultMap.put("status", "not ok");
        return expectedResultMap;
    }

    private Map<String, Object> expectRpcResult(List<Map<String, String>> expectedSearchResultData) {
        Map<String, Object> expectedResultMap = Maps.newHashMap();
        expectedResultMap.put("status", OpenSubtitleAction.STATUS_OK);
        expectedResultMap.put("data", expectedSearchResultData.toArray());
        return expectedResultMap;
    }

    private void expectOpenSubtitleRpcQuery(Map<String, Object> expectedRpcResult) {
        Map<String, String> criteria = Maps.newHashMap();
        criteria.put("imdbid", IMDB_ID);
        criteria.put("sublanguageid", ISO_LANGUAGE_CODE);
        List<Object> rpcParams = Lists.newArrayList(criteria);

        expect(rpcClientMock.execute(eq(SearchAction.RPC_METHOD_NAME), eq(Lists.newArrayList(LOGIN_TOKEN.getToken(), rpcParams)))).andReturn(expectedRpcResult);
    }

    private List<Map<String, String>> replayAndVerify(IMocksControl mocksControl) {
        Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);

        mocksControl.replay();
        List<Map<String, String>> result = searchAction.execute(new SearchCriteria(LOGIN_TOKEN, IMDB_ID, ISO_LANGUAGE_CODE));
        mocksControl.verify();
        return result;
    }
}