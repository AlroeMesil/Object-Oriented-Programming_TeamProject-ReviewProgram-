package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import mgr.Manageable;

public class Ranking {
//    private ArrayList<User> users;
//
//    public Ranking(ArrayList<User> users) {
//        this.users = users;
//    }
//
//    public void updateRanking(ArrayList<Post> posts) {
//        for (User user : users) {
//            int userLike = calculateUserLike(posts, user.id);
//            user.setUserLike(userLike);
//        }
//        Collections.sort(users, Comparator.comparingInt(User::getUserLike).reversed());
//    }
//
//    private int calculateUserLike(ArrayList<Post> postLists, String userId) {
//        int userLike = 0;
//        for (Post post : postLists) {
//            if (post.getGoodPoint().contains(userId)) {
//                userLike += post.getGoodPoint().size();
//            }
//        }
//        return userLike;
//    }
//
//    public void printRanking() {
//        System.out.println("User Ranking:");
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            System.out.printf("%d. %s (좋아요: %d)\n", i + 1, user.getId(), user.getUserLike());
//        }
//    }
    
	// 지역별 게시글 랭킹 출력
    public void printPostsByRegionRanking(ArrayList<Manageable> postList) {
    	System.out.println("========================================================");
        System.out.println("지역 랭킹:");

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
        for (int i = 0; i < sortedRegions.size(); i++) {
            String region = sortedRegions.get(i);
            int count = regionCount.get(region);
            System.out.printf("%d등 %s: %d개\n", i + 1, region, count);
        }
        System.out.println("========================================================");
    }

    // 카테고리 별 게시글 랭킹 출력
    public void printPostsByCategoryRanking(ArrayList<Manageable> postList) {
    	System.out.println("========================================================");
        System.out.println("카테고리 랭킹:");

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
        for (int i = 0; i < sortedCategories.size(); i++) {
            String category = sortedCategories.get(i);
            int count = categoryCount.get(category);
            System.out.printf("%d등 %s: %d개\n", i + 1, category, count);
        }
        System.out.println("========================================================");
    }
    
}
