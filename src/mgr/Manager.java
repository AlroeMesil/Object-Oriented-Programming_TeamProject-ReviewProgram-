package mgr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	public void printAllPost() {
		// TODO Auto-generated method stub
		for (Manageable m: postList) {
			m.print();
			System.out.print("========================================================\n");
		}
	}
	// ==================== 출력 코드 =====================
	
	// ==================== 검색 코드 =====================
	/* 원본 코드	
	public void search(Scanner scan) {
		String name = null;
		while (true) {
			System.out.print("키워드:");
			name = scan.next();
			if (name.equals("end"))
				break;
			for (Manageable m : mList) {
				if (m.matches(name))
					m.print();
			}
		}
	}

	public Manageable find(String kwd) {
		// TODO Auto-generated method stub
		for (Manageable m: mList) {
			if (m.matches(kwd))
				return m;
		}
		return null;
	}*/

	public void searchPostsByKeyword(String keyword) {
	    for (Manageable post : postList) {
	        if (post.matches(keyword)) {
	            post.print();
	            System.out.println("========================================================");
	        }
	    }
	}

	public void searchPostsByWriter(String writerName) {
	    for (Manageable post : postList) {
	        if (post instanceof Post && ((Post) post).postWriter.equals(writerName)) {
	            post.print();
	            System.out.println("========================================================");
	        }
	    }
	}
	// ==================== 검색 코드 =====================
	
	// ==================== CRUD 기능 =====================
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
	public void deletePost(int postId) {
	    Post post = new Post();
	    post.deletePost(postList, postId);
	}
	
	

}
