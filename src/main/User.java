package main;

import mgr.Manageable;

import java.util.Scanner;

public class User implements Manageable {
    String id;
    String pw;
    String name;
    String nickName;
    String email;
    Integer userLike=0;

    User(String id, String pw, String name, String nickName, String email) {
        this.id=id;
        this.pw=pw;
        this.name=name;
        this.nickName=nickName;
        this.email=email;
    }
    public User(String id) {
        this.id = id;
    }
    public User(){}
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof main.User)) {
            return false;
        }
        main.User temp = (main.User) o;

        return id.equals(temp.id);
    }

    @Override
    public String toString() {
        String info = "\nId: " + id + "\n";
        info += "Pw: " + pw + "\n";
        info += "Name: " + name + "\n";
        info += "NickName: " + nickName + "\n";
        info += "email: " + email + "\n";
        info += "userLike: " + userLike + "\n";
        return info;
    }

    @Override
    public void read(Scanner scan) {
        this.id=scan.next();
        this.pw=scan.next();
        this.name=scan.next();
        this.nickName=scan.next();
        this.email=scan.next();
//        this.userLike=scan.nextInt();
    }
    //User출력
    @Override
    public void print() {
        System.out.println(
                "ID: " + id + ", Name: " + name + ", 닉네임:" + nickName+
                        ", Email: " + email+", UserLike: "+userLike);
    }
    //ID, PW랑 kwd비교
    @Override
    public boolean matches(String kwd) {
        if (this.id.equals(kwd)) {
            return true;
        }
        if (this.pw.equals(kwd)) {
            return true;
        }
        return false;
    }
}