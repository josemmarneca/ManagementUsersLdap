package com.dxnet.ldap.commons.utils;

import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

public class FilterUtils {

    public static String getFilterEncode(String[] filterArray){
        AndFilter filter = new AndFilter();
        for (String  filters: filterArray) {
            filter.and(new EqualsFilter("objectclass", filters));
        }
        return filter.encode();
    }
}
