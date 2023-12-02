package mgr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import main.*;

// 코드 재사용성을 올려야함(중복 코드多)

public class Manager {
	public ArrayList<Manageable> userList = new ArrayList<>();
	public ArrayList<Manageable> postList = new ArrayList<>();
	public ArrayList<Manageable> commentList = new ArrayList<>();
	Scanner scanner = new Scanner(System.in);

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

	// 유저의 좋아요 수 계산
	public void setUserLike() {
		for (Manageable user : userList) {
			for (Manageable post : postList) {
				if (user instanceof User && post instanceof Post
						&& ((User) user).id.equals(((Post) post).getPostWriter())) {
					((User) user).userLike += ((Post) post).getGoodPoint().size();
				}
			}
		}
	}

	// 유저 좋아요 수 랭킹
	public ArrayList<User> rankUserLike() {
		ArrayList<User> rankedUsers = new ArrayList<>();
		rankedUsers.addAll(userList.stream()
				.filter(user -> user instanceof User)
				.map(user -> (User) user)
				.collect(Collectors.toList()));

		rankedUsers.sort(Comparator.comparingInt(User::getUserLike).reversed());

		return rankedUsers;
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
		postList.sort(Comparator.comparingInt(post -> ((Post) post).getPostNum()).reversed());
		printAllPost();
	}

	// 게시글 오래된순 출력 메소드
	public void printOldPost() {
		postList.sort(Comparator.comparingInt(post -> ((Post) post).getPostNum()));
		printAllPost();
	}

	// 좋아요 많은순 출력
	public void printPostsByGoodPointDescending() {
		postList.sort(Comparator.comparingInt(o -> ((Post) o).getGoodPoint().size()));
		printAllPost();
	}

	// 게시글 평점 높은 순 출력
	public void printPostsByPostRateDescending() {
		postList.sort(Comparator.comparingInt(o -> ((Post) o).getPostRate()).reversed());
		printAllPost();
	}

	// 게시글 평점 낮은 순 출력
	public void printPostsByPostRateAscending() {
		postList.sort(Comparator.comparingInt(o -> ((Post) o).getPostRate()));
		postList.forEach(post -> ((Post) post).print());
	}

	// 게시글 카테고리로 출력
	public void printPostsByCategory(String category) {
		boolean found = false;
		for (Manageable post : postList) {
			if (post instanceof Post) {
				Post p = (Post) post;
				if (p.getPostCategory().get("category").equalsIgnoreCase(category)) {
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

	// UI 카테고리 검색
	public ArrayList<Manageable> getPostsByCategory(String category) {
		return postList.stream()
				.filter(post -> post instanceof Post)
				.filter(post -> ((Post) post).getPostCategory().get("category").equalsIgnoreCase(category))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	// UI 지역 검색
	public ArrayList<Manageable> getPostsByRegion(String region) {
		return postList.stream()
				.filter(post -> post instanceof Post)
				.filter(post -> ((Post) post).getRegion().equalsIgnoreCase(region))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public ArrayList<Manageable> getPostsByCategoryAndRegion(String category, String region) {
		return postList.stream()
				.filter(post -> post instanceof Post)
				.filter(post -> {
					Post p = (Post) post;
					return (category.equals("전체") || p.getPostCategory().get("category").equalsIgnoreCase(category))
							&& (region.equals("전체") || p.getRegion().equalsIgnoreCase(region));
				})
				.collect(Collectors.toCollection(ArrayList::new));
	}


	// 게시글 평점 이상으로 출력
	public void printPostsByRate(int rate) {
		System.out.print("평점 이상을 입력하세요: ");
		boolean found = false;
		for (Manageable post : postList) {
			if (post instanceof Post) {
				Post p = (Post) post;
				if (p.getPostRate() >= rate) {
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

	// 키워드 검색 메소드(UI)
	public ArrayList<Manageable> searchPostsByKeywordUI(String keyword) {
		return postList.stream()
				.filter(post -> post.matches(keyword))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	// 작성자 이름 검색 메소드
	public void searchPostsByWriter(String writerName) {
		for (Manageable post : postList) {
			if (post instanceof Post && ((Post) post).getPostWriter().equals(writerName)) {
				post.print();
				System.out.println("========================================================");
			}
		}
	}

	// ==================== 검색 코드 =====================

	// ================= 유저 CRUD 기능 ==================
	// 유저추가
	public void addUserList(User newUser) {
		userList.add(newUser);
	}

	public void readAllUser(String filename) {
		Scanner filein = openFile(filename);
		User user = null;
		while (filein.hasNext()) {
			user = new User();
			user.read(filein);
			userList.add(user);
		}
		filein.close();
	}

	public boolean SignUp(String userId, String userPw, String userName, String userNickName, String userEmail) {
		String id = userId;
		String password = userPw;
		String name = userName;
		String nickName = userNickName;
		String email = userEmail;

		User newUser = new User(id, password, name, nickName, email);

		if (userIdCheck(newUser)) {
			System.out.println("\n회원 추가 성공");
			userList.add(newUser);
			System.out.println(userList);
			return true;
		} else {
			System.out.println("아이디 중복으로 회원 추가 실패");
			return false;
		}
	}

	// 아이디 중복확인
	public boolean userIdCheck(User newUser) {
		if (isIdOverlap(newUser.id))
			return false;
		return true;
	}

	// 아이디 중복 확인
	public boolean isIdOverlap(String id) {
		for (Manageable user : userList) {
			if (user instanceof User && ((User) user).id.equals(id)) {
				return true;
			}
		}
		return false;
	}

	// 로그인
	public String login(String userId, String userPw) {
		String id = userId;
		String pw = userPw;
		if (contains(id, pw)) {
			System.out.println("로그인 성공!");
			return id;
		} else{
			System.out.println("로그인 실패!");
		}
		return null;
	}

	// 회원 삭제
	public void removeUser() {
		System.out.println("\n회원 삭제를 시작합니다.");
		System.out.print("삭제할 회원의 아이디를 입력하세요: ");
		String removeId = scanner.next();
		if (withdraw(removeId)) {
			System.out.println("회원 삭제 성공");
		} else {
			System.out.println("해당 아이디의 회원이 없음");
		}
	}

	// 회원 삭제
	public boolean withdraw(String id) {
		for (Manageable user : userList) {
			if (user.matches(id)) {
				userList.remove(user);
				return true;
			}
		}
		return false;
	}

	// 유저 정보 가져오기
	public User getUser(String id) {
		for (Manageable user : userList) {
			if (user instanceof User && ((User) user).matches(id)) {
				return (User) user;
			}
		}
		return null;
	}

	// 회원 여부 확인
	public boolean contains(String id, String pw) {
		for (Manageable user : userList) {
			if (user instanceof User) {
				if (((User) user).matches(id) && ((User) user).matches(pw))
					return true;
			}
		}
		return false;
	}

	// 회원정보 조회
	public void userMatches() {
		System.out.println("회원 정보 조회를 시작합니다.");
		System.out.print("조회할 회원의 아이디를 입력하세요: ");
		String getInfoId = scanner.next();
		User userInfo = getUser(getInfoId);

		if (userInfo != null) {
			userInfo.print();
		} else {
			System.out.println("해당 아이디의 회원이 없음");
		}
	}

	// ================= 유저 CRUD 기능 ==================

	// ================= 게시글 CRUD 기능 ==================
	// Create
	public void addPostList(String userId) {
		Post post = new Post();
		post.createPost(postList, userId);
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
	public void editPost(int postId, String userId) {
		for (Manageable post : postList) {
			if (post instanceof Post && ((Post) post).getPostNum() == postId) {
				Post editablePost = (Post) post;
				if (userId.equals(editablePost.getPostWriter())) {
					editablePost.updatePost();
				} else {
					System.out.println("게시글 작성자가 아닙니다.");
				}
				return;
			}
		}
		System.out.println("일치하는 게시글이 없습니다.");
	}

	// Delete
	public void deletePost(int postId, String userId) {
		for (Manageable post : postList) {
			if (post instanceof Post && ((Post) post).getPostNum() == postId) {
				Post deleteablePost = (Post) post;
				if (userId.equals(deleteablePost.getPostWriter())) {
					deleteablePost.deletePost(postList, postId, userId);
					// 해당 게시글의 이미지 데이터 삭제
					File imageFile = new File("../TeamB_ReviewApp/images/" + postId + ".png");
					if (imageFile.exists()) {
						if (imageFile.delete()) {
							System.out.println("게시글 이미지 파일이 삭제되었습니다.");
						} else {
							System.out.println("게시글 이미지 파일 삭제 실패");
						}
					} else {
						System.out.println("게시글 이미지 파일이 존재하지 않습니다.");
					}
					return;
				} else {
					System.out.println("게시글 작성자가 아닙니다.");
					return;
				}
			}
		}
		System.out.println("일치하는 게시글이 없습니다.");
	}
	// ================= 게시글 CRUD 기능 ==================

	// ================= 게시글 평가 기능 ==================
	// 게시글 좋아요 메소드
	public String controlGoodPointToPost(String userId, int postId) {
		String checker = "";
		for (Manageable post : postList) {
			if (post instanceof Post && ((Post) post).getPostNum() == postId) {
				checker = ((Post) post).controlGoodPoint(userId);
			}
		}
		return checker;
	}

	// 게시글 좋아요 삭제 메소드 -> UI 부분에서 사용 X
	public boolean deleteGoodPointFromPost(String userId, int postId) {
		boolean checker = false;
		for (Manageable post : postList) {
			if (post instanceof Post && ((Post) post).getPostNum() == postId) {
				checker = ((Post) post).deleteGoodPoint(userId);
			}
		}
		if(checker == true) return true;
		else return false;
		//System.out.println("일치하는 게시글이 없습니다.");
	}

	// 게시글 싫어요 메소드
	public String controlBadPointToPost(String userId, int postId) {
		String checker = "";
		for (Manageable post : postList) {
			if (post instanceof Post && ((Post) post).getPostNum() == postId) {
				checker = ((Post) post).controlBadPoint(userId);
			}
		}
		return checker;
	}

	// 게시글 싫어요 삭제 메소드 -> UI 부분에서 사용 X
	public void deleteBadPointFromPost(String userId, int postId) {
		for (Manageable post : postList) {
			if (post instanceof Post && ((Post) post).getPostNum() == postId) {
				((Post) post).deleteBadPoint(userId);
				return;
			}
		}

		System.out.println("일치하는 게시글이 없습니다.");
	}
	// ================= 게시글 평가 기능 ==================

	// ================= 랭킹 클래스 관리 ==================
	public void printRegionRanking(Ranking rank) {
		rank.printPostsByRegionRanking(postList);
	}

	public void printCategoryRanking(Ranking rank) {
		rank.printPostsByCategoryRanking(postList);
	}

	public void printUserRanking(Ranking rank, ArrayList<User> rankedUserRanking) {
		rank.printUserRank(rankedUserRanking);
	}
	// ================= 랭킹 클래스 관리 ==================

	// ================= 댓글 클래스 관리 ==================
	public void readAllComment(String filename) {
		Scanner filein = openFile(filename);
		Comment comment = null;
		while (filein.hasNext()) {
			comment = new Comment();
			comment.read(filein);
			commentList.add(comment);
		}
		filein.close();
	}

	public void controlCommentList(int postId, String userId, String postComment) {
		Comment comment = new Comment();
		comment.createComment(commentList, userId, postComment, postId);
		commentList.add(comment);
		System.out.println("댓글이 입력되었습니다.");
	}

	public void deleteComment(int commentId, String userId, String postComment) {
		for (Manageable comment : postList) {
			if (comment instanceof Comment && ((Comment) comment).getCommentId() == commentId) {
				Comment deleteablecomment = (Comment) comment;
				if (userId.equals(deleteablecomment.getCommentWriter())) {
					deleteablecomment.deleteComment(commentList, commentId, userId);
				}
			}
			System.out.println("일치하는 게시글이 없습니다.");
		}
	}

	public ArrayList<Comment> searchComment(int postId) {
		ArrayList<Comment> commentList = new ArrayList<>();
		for (Manageable comment : this.commentList) {
			if (comment instanceof Comment && ((Comment) comment).getPostId() == postId) {
				Comment searchedComment = (Comment) comment;
				commentList.add(searchedComment);
			}
		}
		return commentList;
	}
	// ================= 댓글 클래스 관리 ==================
}