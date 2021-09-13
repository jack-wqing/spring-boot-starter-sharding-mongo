package com.bj58.chr.ai.sharding.mongo.constant;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author liuwenqing02
 */
public class NumberUtils {

    public static final Set<Character> numbers = Sets.newHashSet('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    public static final boolean isContain(Character character){
        return numbers.contains(character);
    }

}