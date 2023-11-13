package mgr;

import java.util.Scanner;

import main.Main;

public interface Manageable {
	public void read(Scanner scan);
	public void print();
	public boolean matches(String kwd);
}
