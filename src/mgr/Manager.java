package mgr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import main.*;

// 코드 재사용성을 올려야함(중복 코드多)

public class Manager {
	ArrayList<Manageable> userList = new ArrayList<>();
	ArrayList<Manageable> postList = new ArrayList<>();
	
	public Scanner openFile(String filename) {
		Scanner filein = null;
		try {
			filein = new Scanner(new File(filename));
		} catch (IOException e)
		{
			System.out.println("파일 입력 오류");
			System.exit(0);
		}
		return filein;
	}
	
	// ==================== 출력 코드 =====================
	public void printAllUser() {
		// TODO Auto-generated method stub
		for (Manageable m: userList) {
			m.print();
			System.out.print("========================================================\n");
		}
	}
	
	// 게시글 출력 메소드
	public void printAllPost() {
		// TODO Auto-generated method stub
		for (Manageable m: postList) {
			m.print();
			System.out.print("========================================================\n");
		}
	}
	
	// 게시글 최신순 출력 메소드
	public void printRecentPost() {
		postList.sort(Comparator.comparingInt(post -> ((Post) post).postNum).reversed());
        printAllPost();
	}
	
	// 게시글 오래된순 출력 메소드
	public void printOldPost() {
		postList.sort(Comparator.comparingInt(post -> ((Post) post).postNum));
        printAllPost();
	}
	
	// 좋아요 많은순 출력
    public void printPostsByGoodPointDescending() {
        postList.sort(Comparator.comparingInt(o -> ((Post) o).goodPoint.size()));
        printAllPost();
    }
    
    // 게시글 평점 높은 순 출력
    public void printPostsByPostRateDescending() {
        postList.sort(Comparator.comparingInt(o -> ((Post) o).postRate).reversed());
        printAllPost();
    }

    // 게시글 평점 낮은 순 출력
    public void printPostsByPostRateAscending() {
        postList.sort(Comparator.comparingInt(o -> ((Post) o).postRate));
        postList.forEach(post -> ((Post) post).print());
    }
    
    // 게시글 카테고리로 출력
    public void printPostsByCategory(String category) {

        boolean found = false;

        for (Manageable post : postList) {
            if (post instanceof Post) {
                Post p = (Post) post;
                if (p.postCategory.get("category").equalsIgnoreCase(category)) {
                    p.print();
                    found = true;
                    System.out.println("========================================================");
                }
            }
        }

        if (!found) {
            System.out.println("일치하는 게시글이 없습니다.");
        }
    }
    
    // 게시글 평점 이상으로 출력
    public void printPostsByRate(int rate) {
        System.out.print("평점 이상을 입력하세요: ");
        boolean found = false;

        for (Manageable post : postList) {
            if (post instanceof Post) {
                Post p = (Post) post;
                if (p.postRate >= rate) {
                    p.print();
                    found = true;
                    System.out.println("========================================================");
                }
            }
        }

        if (!found) {
            System.out.println("일치하는 게시글이 없습니다.");
        }
    }

    /*// 좋아요 적은순 출력
    public void printPostsByGoodPointAscending() {
    	List<Post> posts = postList.stream()
                .filter(post -> post instanceof Post)
                .map(post -> (Post) post)
                .collect(Collectors.toList());
        posts.sort(Comparator.comparingInt(o -> o.goodPoint.size()));
        posts.forEach(Post::print);
        printAllPost();
    }*/
	// ==================== 출력 코드 =====================
	
	// ==================== 검색 코드 =====================
	// 키워드 검색 메소드
	public void searchPostsByKeyword(String keyword) {
	    for (Manageable post : postList) {
	        if (post.matches(keyword)) {
	            post.print();
	            System.out.println("========================================================");
	        }
	    }
	}
	
	// 작성자 이름 검색 메소드
	public void searchPostsByWriter(String writerName) {
	    for (Manageable post : postList) {
	        if (post instanceof Post && ((Post) post).postWriter.equals(writerName)) {
	            post.print();
	            System.out.println("========================================================");
	        }
	    }
	}
	// ==================== 검색 코드 =====================
	
	// ================= 게시글 CRUD 기능 ==================
	// Create
	public void addPostList(String userId) {
		Post post = new Post();
		User user = new User(userId);
		post.createPost(postList, user);
		postList.add(post);
	}
	
	// Read
	public void readAllPost(String filename) {
		Scanner filein = openFile(filename);
		Post post = null;
		while (filein.hasNext()) {
			post = new Post();
			post.read(filein);
			postList.add(post);
		}
		filein.close();
	}
	
	// Update
	public void editPost(int postId) {
	    for (Manageable post : postList) {
	        if (post instanceof Post && ((Post) post).postNum == postId) {
	            Post editablePost = (Post) post;
	            editablePost.updatePost();
	            return;
	        }
	    }
	    System.out.println("일치하는 게시글이 없습니다.");
	}
	
	// Delete
	public void deletePost(int postId, String userId) {
	    Post post = new Post();
	    post.deletePost(postList, postId, userId);
	}
	// ================= 게시글 CRUD 기능 ==================
	
	// ================= 게시글 평가 기능 ==================
	// 게시글 좋아요 메소드
    public void addGoodPointToPost(String userId, int postId) {
        for (Manageable post : postList) {
            if (post instanceof Post && ((Post) post).postNum == postId) {
                ((Post) post).addGoodPoint(userId);
                return;
            }
        }
        System.out.println("일치하는 게시글이 없습니다.");
    }

    // 게시글 좋아요 삭제 메소드
    public void deleteGoodPointFromPost(String userId, int postId) {
        for (Manageable post : postList) {
            if (post instanceof Post && ((Post) post).postNum == postId) {
                ((Post) post).deleteGoodPoint(userId);
                return;
            }
        }
        System.out.println("일치하는 게시글이 없습니다.");
    }

    // 게시글 싫어요 메소드
    public void addBadPointToPost(String userId, int postId) {
        for (Manageable post : postList) {
            if (post instanceof Post && ((Post) post).postNum == postId) {
                ((Post) post).addBadPoint(userId);
                return;
            }
        }
        System.out.println("일치하는 게시글이 없습니다.");
    }

    // 게시글 싫어요 삭제 메소드
    public void deleteBadPointFromPost(String userId, int postId) {
        for (Manageable post : postList) {
            if (post instanceof Post && ((Post) post).postNum == postId) {
                ((Post) post).deleteBadPoint(userId);
                return;
            }
        }
        System.out.println("일치하는 게시글이 없습니다.");
    }
	

}
