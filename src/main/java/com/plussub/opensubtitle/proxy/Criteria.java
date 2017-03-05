package com.plussub.opensubtitle.proxy;

/**
 * Created by stefa on 05.03.2017.
 */
public interface Criteria {

    static VoidCriteria VOID_CRITERIA = new VoidCriteria();

    class VoidCriteria implements Criteria{
        private VoidCriteria(){
        }
    }
}
