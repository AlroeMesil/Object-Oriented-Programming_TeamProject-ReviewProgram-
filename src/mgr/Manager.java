package mgr;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import main.*;

// 코드 재사용성을 올려야함(중복 코드多)

public class Manager {
	ArrayList<Manageable> userList = new ArrayList<>();
	ArrayList<Manageable> postList = new ArrayList<>();
	Scanner scanner = new Scanner(System.in);

	public Scanner openFile(String filename) {
		Scanner filein = null;
		try {
			filein = new Scanner(new File(filename));
		} catch (IOException e) {
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
						&& ((User) user).id.equals(((Post) post).postWriter)) {
					((User) user).userLike += ((Post) post).goodPoint.size();
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
	// User전체 출력
	public void printAllUser() {
		System.out.println("\n전체 회원 정보를 출력합니다.");
		for (Manageable user : userList) {
			user.print();
		}
	}

	// 게시글 출력 메소드
	public void printAllPost() {
		for (Manageable m : postList) {
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

	public void SignUp() {
		System.out.println("계정 생성.");
		System.out.print("아이디를 입력하세요: ");
		String id = scanner.next();
		System.out.print("비밀번호를 입력하세요: ");
		String password = scanner.next();
		System.out.print("이름을 입력하세요: ");
		String name = scanner.next();
		System.out.print("닉네임을 입력하세요: ");
		String nickName = scanner.next();
		System.out.print("이메일을 입력하세요: ");
		String email = scanner.next();

		User newUser = new User(id, password, name, nickName, email);

		if (userIdCheck(newUser)) {
			System.out.println("\n회원 추가 성공");
			userList.add(newUser);
		} else {
			System.out.println("아이디 중복으로 회원 추가 실패");
		}
	}

	// 아이디 중복확인
	public boolean userIdCheck(User newUser) {
		if (isIdOverlap(newUser.id))
			return false;
		userList.add(newUser);
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
	public String login() {
		System.out.print("ID: ");
		String id = scanner.next();
		System.out.print("PW: ");
		String pw = scanner.next();
		if (contains(id, pw)) {
			System.out.println("로그인 성공!");
			return id;
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
