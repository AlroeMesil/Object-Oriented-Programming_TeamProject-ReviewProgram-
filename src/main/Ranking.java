package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import mgr.Manageable;

public class Ranking {

    // 사용자 인기(좋아요) 랭킹 출력
    public void printUserRank(ArrayList<User> rankedUserList){
        int num=1;
        for (User user: rankedUserList){
            System.out.print("<"+num+"위>  ");
            user.print();
            num++;
        }
    }

    // 지역별 게시글 랭킹 출력
    public String printPostsByRegionRanking(ArrayList<Manageable> postList) {
        StringBuilder result = new StringBuilder();
        result.append("========================================================\n");
        result.append("지역 랭킹:\n");

        // 각 지역의 게시글 수 세기
        Map<String, Integer> regionCount = new HashMap<>();
        for (Manageable post : postList) {
            String region = ((Post) post).getRegion();
            regionCount.put(region, regionCount.getOrDefault(region, 0) + 1);
        }

        // 지역별 게시글 수를 내림차순으로 정렬
        ArrayList<String> sortedRegions = new ArrayList<>(regionCount.keySet());
        sortedRegions.sort(Comparator.comparingInt(regionCount::get).reversed());

        // 각 지역의 게시글 수 출력
        for (int i = 0; i < 5; i++) {
            String region = sortedRegions.get(i);
            int count = regionCount.get(region);
            System.out.printf("%d등 %s: %d개\n", i + 1, region, count);
            result.append(String.format("<%d등> %s: %d개\n", i + 1, region, count));
        }
        System.out.println("========================================================");

        result.append("========================================================\n");
        return result.toString();
    }


    // 카테고리 별 게시글 랭킹 출력
    public String printPostsByCategoryRanking(ArrayList<Manageable> postList) {
        StringBuilder result = new StringBuilder();
        result.append("========================================================\n");
        result.append("카테고리 랭킹:\n");

        // 각 카테고리의 게시글 수 세기
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Manageable post : postList) {
            String category = ((Post) post).getPostCategory().get("category");
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        // 카테고리별 게시글 수를 내림차순으로 정렬
        ArrayList<String> sortedCategories = new ArrayList<>(categoryCount.keySet());
        sortedCategories.sort(Comparator.comparingInt(categoryCount::get).reversed());

        // 각 카테고리의 게시글 수 출력
        for (int i = 0; i < 5; i++) {
            String category = sortedCategories.get(i);
            int count = categoryCount.get(category);
            System.out.printf("%d등 %s: %d개\n", i + 1, category, count);
            result.append(String.format("<%d등> %s: %d개\n", i + 1, category, count));
        }
        System.out.println("========================================================");

        result.append("========================================================\n");
        return result.toString();
    }

    //게시글 별 좋아요 랭킹 출력
    public String printPostsByGoodPointDescending(ArrayList<Manageable> postList) {
        // 게시글 좋아요 확인
        postList.sort(Comparator.comparingInt(o -> ((Post) o).getGoodPoint().size()).reversed());

        StringBuilder result = new StringBuilder();
        result.append("========================================================\n");
        result.append("게시글 랭킹:\n");

        // 각 게시글의 좋아요 출력
        int num = 1;
        for (Manageable post : postList) {
            if (post instanceof Post) {
                result.append(String.format("<%d등> ", num));
                result.append(((Post) post).getTitle());
                result.append(String.format(" (좋아요 %d개)\n", ((Post) post).getGoodPoint().size()));
                num++;
                if (num > 5) {
                    break;
                }
            }
        }

        result.append("========================================================\n");
        return result.toString();
    }

}