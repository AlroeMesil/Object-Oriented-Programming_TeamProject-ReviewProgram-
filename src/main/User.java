package main;

public class User {
	String id;
    private String pw;
    private String name;
    private String nickName;
    private String email;
    
    public User() {
		// TODO Auto-generated constructor stub
		
	}

    public User(String id, String pw, String name, String nickName, String email) {
        setId(id);
        setPw(pw);
        setName(name);
        setNickName(nickName);
        setEmail(email);
    }

    public User(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) {
            return false;
        }
        User temp = (User) o;

        return id.equals(temp.getId());
    }

    @Override
    public String toString() {
        String info = "\nId: " + id + "\n";
        info += "Pw: " + pw + "\n";
        info += "Name: " + name + "\n";
        info += "NickName: " + nickName + "\n";
        info += "email: " + email + "\n";
        return info;
    }
}