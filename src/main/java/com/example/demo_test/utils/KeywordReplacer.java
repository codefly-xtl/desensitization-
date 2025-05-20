package com.example.demo_test.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 关键词替换工具类，支持将字符串中的指定关键词替换为星号
 */
public class KeywordReplacer {

    /**
     * 将文本中的所有关键词替换为等长的星号（默认大小写敏感）
     * @param text 原始文本
     * @param keywords 关键词列表
     * @return 替换后的文本
     */
    public static String replaceWithAsterisks(String text, List<String> keywords) {
        return replaceWithAsterisks(text, keywords, false);
    }

    /**
     * 将文本中的所有关键词替换为等长的星号
     * @param text 原始文本
     * @param keywords 关键词列表
     * @param caseInsensitive 是否忽略大小写，true表示不区分大小写
     * @return 替换后的文本
     */
    public static String replaceWithAsterisks(String text, List<String> keywords, boolean caseInsensitive) {
        if (text == null || text.isEmpty() || keywords == null || keywords.isEmpty()) {
            return text;
        }
        
        // 构建关键词模式，按长度降序排列，避免短词先替换影响长词匹配
        List<String> sortedKeywords = new ArrayList<>(keywords);
        sortedKeywords.sort((k1, k2) -> k2.length() - k1.length());
        
        StringBuilder patternBuilder = new StringBuilder();
        for (String keyword : sortedKeywords) {
            if (!keyword.isEmpty()) {
                if (patternBuilder.length() > 0) {
                    patternBuilder.append("|");
                }
                // 转义关键词中的正则特殊字符
                patternBuilder.append(Pattern.quote(keyword));
            }
        }
        
        if (patternBuilder.length() == 0) {
            return text;
        }
        
        // 编译正则表达式
        int flags = caseInsensitive ? Pattern.CASE_INSENSITIVE : 0;
        Pattern pattern = Pattern.compile(patternBuilder.toString(), flags);
        Matcher matcher = pattern.matcher(text);
        
        // 使用StringBuilder进行替换
        StringBuilder result = new StringBuilder(text);
        int offset = 0;
        
        while (matcher.find()) {
            int start = matcher.start() + offset;
            int end = matcher.end() + offset;
            String replacement = "*".repeat(end - start);
            
            result.replace(start, end, replacement);
            
            // 计算替换后偏移量的变化
            offset += replacement.length() - (end - start);
        }
        
        return result.toString();
    }

    /**
     * 将文本中的所有关键词替换为固定长度的星号
     * @param text 原始文本
     * @param keywords 关键词列表
     * @param fixedLength 固定的星号长度
     * @return 替换后的文本
     */
    public static String replaceWithFixedAsterisks(String text, List<String> keywords, int fixedLength) {
        if (text == null || text.isEmpty() || keywords == null || keywords.isEmpty()) {
            return text;
        }
        
        String replacement = fixedLength > 0 ? "*".repeat(fixedLength) : "";
        
        // 构建关键词模式
        StringBuilder patternBuilder = new StringBuilder();
        for (String keyword : keywords) {
            if (!keyword.isEmpty()) {
                if (patternBuilder.length() > 0) {
                    patternBuilder.append("|");
                }
                patternBuilder.append(Pattern.quote(keyword));
            }
        }
        
        if (patternBuilder.length() == 0) {
            return text;
        }
        
        // 编译正则表达式
        Pattern pattern = Pattern.compile(patternBuilder.toString());
        Matcher matcher = pattern.matcher(text);
        
        // 使用StringBuilder进行替换
        StringBuilder result = new StringBuilder(text);
        int offset = 0;
        
        while (matcher.find()) {
            int start = matcher.start() + offset;
            int end = matcher.end() + offset;
            
            result.replace(start, end, replacement);
            
            // 计算替换后偏移量的变化
            offset += replacement.length() - (end - start);
        }
        
        return result.toString();
    }

    /**
     * 示例用法
     */
    public static void main(String[] args) {
        String text = "晓美焰来到北京立方庭参观自然语义科技公司。肖添龙在2025.05.20去好未来公司实习了";
        List<String> keywords = Arrays.asList("晓美焰", "北京立方庭", "自然语义科技公司", "肖添龙", "2025.05.20", "好未来公司");
        
        // 替换为等长星号
        String replaced1 = replaceWithAsterisks(text, keywords);
        System.out.println("等长替换: " + replaced1);
        
        // 替换为固定长度星号
        String replaced2 = replaceWithFixedAsterisks(text, keywords, 3);
        System.out.println("固定长度替换: " + replaced2);
        
        // 忽略大小写替换
        String textWithMixedCase = "HanLP是优秀的NLP工具，hanlp和HANLP都指同一个项目";
        String replaced3 = replaceWithAsterisks(textWithMixedCase, Arrays.asList("hanlp"), true);
        System.out.println("忽略大小写替换: " + replaced3);
    }
}    