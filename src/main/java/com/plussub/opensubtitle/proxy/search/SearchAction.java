package com.plussub.opensubtitle.proxy.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.plussub.opensubtitle.proxy.OpenSubtitleAction;
import com.plussub.opensubtitle.proxy.rpc.RpcClient;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Created by sonste on 27.02.2017.
 */
public class SearchAction implements OpenSubtitleAction<List<Map<String,String>>,SearchCriteria> {

    private final RpcClient rpcClient;

    static final String RPC_METHOD_NAME ="SearchSubtitles";

    @Inject
    public SearchAction(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    @Override
    public List<Map<String,String>> execute(SearchCriteria criteria) {

        Map<String,?> searchResult = rpcClient.execute(RPC_METHOD_NAME, createPParamsFrom(criteria));

        if(!STATUS_OK.equals(searchResult.get("status"))){
            throw new SearchException("Status was: "+searchResult.get("status"));
        }

        List<Object> foundedSubtitles =  Arrays.asList((Object[]) searchResult.get("data"));

        return foundedSubtitles.stream()
                .parallel()
                .map(obj -> (Map<String,String>) obj)
                .filter(SearchAction::onlySupportedFormats)
                .filter(SearchAction::onlySupportedEncodings)
                .sorted(comparing(SearchAction::byRating).reversed())
                .collect(toList());
    }

    /**
     * XML RPC Call:
     * array SearchSubtitles( $token, array(array('sublanguageid' => $sublanguageid, 'moviehash' => $moviehash, 'moviebytesize' => $moviesize, imdbid => $imdbid, query => 'movie name', "season" => 'season number', "episode" => 'episode number', 'tag' => tag ),array(...)), array('limit' => 500))
     * @param criteria
     * @return
     */
    private ArrayList<Object> createPParamsFrom(SearchCriteria criteria) {
        return Lists.newArrayList(criteria.getLoginToken().getToken(), createSearchQueryFrom(criteria));
    }


    private List<Map<String, String>> createSearchQueryFrom(SearchCriteria criteria) {
        Map<String, String> searchCriteriaMap = Maps.newHashMap();
        searchCriteriaMap.put("imdbid", criteria.getImdbId());
        searchCriteriaMap.put("sublanguageid", criteria.getIsoLanguageCode());
        return Lists.newArrayList(searchCriteriaMap);
    }

    private static boolean onlySupportedFormats(Map<String,String> entry){
        return "srt".equals(entry.get("SubFormat")) || "vtt".equals(entry.get("SubFormat"));
    }

    private static boolean onlySupportedEncodings(Map<String,String> entry){
        String encoding = entry.get("SubEncoding");
        return "UTF-8".equalsIgnoreCase(encoding) || "ASCII".equalsIgnoreCase(encoding);
    }

    private static double byRating(Map<String,String> e){
        return Double.parseDouble(e.get("SubRating"));
    }

}
