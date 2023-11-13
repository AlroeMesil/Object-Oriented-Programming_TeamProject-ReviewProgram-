package main;

import java.util.ArrayList;
import java.util.Scanner;

public class UserData {
    Scanner scanner = new Scanner(System.in);
    private ArrayList<User> users;

    public UserData() {
        users = new ArrayList<User>();
    }
    //회원가입
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
        } else {
            System.out.println("아이디 중복으로 회원 추가 실패");
        }
    }
    // 아이디 중복확인

    public boolean userIdCheck(User user) {
        if (!isIdOverlap(user.getId())) {
            users.add(user);
            return true;
        }
        return false;
    }

    // 아이디 중복 확인
    public boolean isIdOverlap(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    //로그인
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

    //회원 삭제
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
        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    // 유저 정보 가져오기
    public User getUser(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    // 회원인지 아닌지 확인
    public boolean contains(String id,String pw) {
        for (User user : users) {
            if (!user.getId().equals(id)) {
                System.out.println("ID불일치");
                return false;
            }
            if (!user.getPw().equals(pw)) {
                System.out.println("PW불일치");
                return false;
            }
        }
        return true;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }
    //User전체 출력
    public void printAllUser() {
        System.out.println("\n전체 회원 정보를 출력합니다.");
        ArrayList<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            printUser(user);
        }
    }
    //User출력
    public void printUser(User user){
        System.out.println(
                "ID: " + user.getId() + ", Name: " + user.getName()
                        + ", 닉네임:" + user.getNickName()+ ", Email: "
                        + user.getEmail());
    }
    //회원정보 조회
    public void userMatches() {
        System.out.println("회원 정보 조회를 시작합니다.");
        System.out.print("조회할 회원의 아이디를 입력하세요: ");
        String getInfoId = scanner.next();
        User userInfo = getUser(getInfoId);

        if (userInfo != null) {
            printUser(userInfo);
        } else {
            System.out.println("해당 아이디의 회원이 없음");
        }
    }


}

