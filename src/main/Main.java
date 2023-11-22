package main;

import java.util.Scanner;
import mgr.Manager;

public class Main {
	String userId = null;
	void userFunc(UserData userData, Scanner scanner) {
		while (true) {
            System.out.println("[user 관련 기능]\n" +
                    "1. 회원가입\n" +
                    "2. 로그인\n"+
                    "3. 전체 회원 정보 출력\n" +
                    "4. 회원 삭제\n" +
                    "5. 회원 정보 조회\n" +
                    "6. 뒤로가기");
            System.out.print(">>메뉴를 선택하세요: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // 회원 가입
                    userData.SignUp();
                    break;
                case 2:
                    //로그인
                	userId = userData.login();
                    break;
                case 3:
                    // 전체 회원 정보 출력
                    userData.printAllUser();
                    break;
                case 4:
                    // 회원 삭제
                    userData.removeUser();
                    break;
                case 5:
                    // 회원 정보 조회
                    userData.userMatches();
                    break;
                case 6:
                    // 종료
                    System.out.println("선택창으로 돌아갑니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
            break;
        }
	}
	void postFunc(Manager mgr, Scanner scanner, String userId) {
		while(true) {
			System.out.println("[게시글 관련 기능]\n" +
                    "1. 전체 게시글 정보 출력\n" +
                    "2. 게시글 업로드\n"+
                    "3. 게시글 검색\n" +
                    "4. 게시글 수정\n" +
                    "5. 게시글 삭제\n" +
                    "6. 게시글 정렬\n" +
                    "7. 평가\n" +
                    "8. 뒤로가기\n"
                    );
            System.out.print(">>메뉴를 선택하세요: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                	System.out.println("======================= 게시글 출력 =======================");
					mgr.printAllPost();
					break;
                case 2:
                	System.out.println("======================= 게시글 업로드 ======================="); 
                    if (userId != null) {
                        mgr.addPostList(userId);
                        scanner.nextLine();
                        mgr.printAllPost();
                    } else {
                        System.out.println("로그인에 실패하였습니다. 다시 시도하세요.");
                    }
                    break;
                case 3:
                	System.out.println("======================= 게시글 검색/분류 =======================");
				    int searchType = 0;
				    while(searchType >=1 || searchType <= 3) {
				    	System.out.print("(1)키워드 검색 (2)작성자 검색 (3)카테고리 검색 (4)좋아요 필터 (기타)종료\n");
					    searchType = scanner.nextInt();
					    scanner.nextLine();
					    if (searchType == 1) {
					    	System.out.print("키워드를 입력하세요: ");
					        String keyword = scanner.next();
					        mgr.searchPostsByKeyword(keyword);
					    } else if (searchType == 2) {
					        System.out.print("작성자의 닉네임을 입력하세요: ");
					        String writerName = scanner.next();
					        mgr.searchPostsByWriter(writerName);
					    } else if(searchType == 3) {
					    	System.out.print("카테고리를 입력하세요: "); 
					        String category = scanner.next(); // 실제 프로그램에서는 버튼 또는 체크박스를 통한 값 인풋
					    	mgr.printPostsByCategory(category);
					        break;
					    }
					    else if(searchType == 4) {
					    	System.out.print("평점을 입력하세요: "); 
					        int rate = scanner.nextInt(); // 실제 프로그램에서는 버튼 또는 체크박스를 통한 값 인풋
					    	mgr.printPostsByRate(rate);
					        break;
					    }
					    else 
					        break;
				    }
				    break;
                case 4:
                	System.out.println("================ 게시글 수정 ================");
				    System.out.print("수정할 게시글의 ID를 입력하세요: ");
				    int postIdToEdit = scanner.nextInt();
				    scanner.nextLine();
				    mgr.editPost(postIdToEdit, userId);
				    break;
                case 5:
                	System.out.println("================ 게시글 삭제 ================");
				    System.out.print("삭제할 게시글의 ID를 입력하세요: ");
				    int postIdToDelete = scanner.nextInt();
				    scanner.nextLine();
				    mgr.deletePost(postIdToDelete, userId);
				    break;
                case 6:
                	System.out.println("================ 게시글 정렬 ================");
                	System.out.print("(1)최신순 정렬 (2)오래된순 정렬\n");
                	int sortSelection = scanner.nextInt();
                	if(sortSelection == 1) {
                		mgr.printRecentPost();
                	} else if(sortSelection == 2) {
                		mgr.printOldPost();
                	} else {
                		System.out.print("잘못된 선택입니다.");
					}
                    break;
                case 7:
                	System.out.println("================ 게시글 평가 ================");
                	System.out.print("(1)좋아요 추가 (2)좋아요 삭제 (3)싫어요 추가 (4)싫어요 삭제\n");
                	int rateSelection = scanner.nextInt();
                	if(rateSelection == 1) {
                		System.out.print("좋아요 추가 할 게시글 ID를 입력해주세요.(실제 프로그램에서는 해당 페이지의 ID값 참조) >> ");
                		int postId = scanner.nextInt();
                		mgr.addGoodPointToPost(userId, postId);
                	} else if(rateSelection == 2) {
                		System.out.print("좋아요 삭제 할 게시글 ID를 입력해주세요.(실제 프로그램에서는 해당 페이지의 ID값 참조) >> ");
                		int postId = scanner.nextInt();
                		mgr.deleteGoodPointFromPost(userId, postId);
                	} else if(rateSelection == 3) {
                		System.out.print("싫어요 추가 할 게시글 ID를 입력해주세요.(실제 프로그램에서는 해당 페이지의 ID값 참조) >> ");
                		int postId = scanner.nextInt();
                		mgr.addBadPointToPost(userId, postId);
                	} else if(rateSelection == 4) {
                		System.out.print("싫어요 삭제 할 게시글 ID를 입력해주세요.(실제 프로그램에서는 해당 페이지의 ID값 참조) >> ");
                		int postId = scanner.nextInt();
                		mgr.deleteBadPointFromPost(userId, postId);
                	}
                	else {
                		System.out.print("잘못된 선택입니다.");
					}
                    break;
                case 8:
                	System.out.println("선택창으로 돌아갑니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
            break;
		}
	}
	void rankFunc(Manager mgr, Scanner scanner, Ranking rank) {
		while(true) {
			System.out.println("[랭킹 기능]\n" +
					"1. 유저 인기(좋아요) 랭킹\n" +
					"2. 지역 랭킹\n"+
					"3. 카테고리 랭킹\n" +
	                "4. 뒤로가기"
	                );
			System.out.print(">>메뉴를 선택하세요: ");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1: {
//				mgr.printRegionRanking();
				break;
			}
			case 2: {
				mgr.printRegionRanking(rank);
				break;
			}
			case 3: {
				mgr.printCategoryRanking(rank);
				break;
			}
			case 4: {
				System.out.println("선택창으로 돌아갑니다.");
				break;
			}
			default:
				System.out.println("잘못된 선택입니다. 다시 선택하세요.");
			}
			break;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();
		Manager mgr = new Manager();
		UserData userData = new UserData();
		Ranking rank = new Ranking();
		Scanner scanner = new Scanner(System.in);
		mgr.readAllPost("postList.txt");
		mgr.readAllUser("userListData.txt");
		int type = 0;
		System.out.println("========================================================");
		while (true) {
			while(true) {
				System.out.print("(1)user 관련 기능 (2)게시글 관련 기능 (3)랭킹 기능 (기타)종료\n");
				type = scanner.nextInt();
				if(type == 1) {
					main.userFunc(userData, scanner);
				} else if(type == 2) {
					System.out.println(main.userId); //userId 확인용
					main.postFunc(mgr,scanner, main.userId);
				} else if(type == 3){
					main.rankFunc(mgr, scanner, rank);
				}else {
					System.out.println("프로그램을 종료합니다.");
					scanner.close();
                    System.exit(0);
				}
			}		
		}
	}
}