package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import mgr.Manageable;

public class Comment implements Manageable {
	private int postId;
	private int commentId;
	private String commentWriter;
	private String commentContent;
	
	public void createComment(ArrayList<Manageable> mList, String commentWriter, String commentContent, int postId) {
		this.postId = postId;
		this.commentId = mList.size()+1;
		this.commentWriter = commentWriter;
		this.commentContent = commentContent;
	}
	
	public void deleteComment(ArrayList<Manageable> commentList, int commentId, String userId) {
	    Iterator<Manageable> iterator = commentList.iterator();
	    while (iterator.hasNext()) {
	        Manageable comment = iterator.next();
	        if (comment instanceof Comment && ((Comment) comment).commentId == commentId) {
	            if (userId.equals(((Comment) comment).commentWriter)) {
	                iterator.remove();
	                System.out.println("댓글이 삭제되었습니다.");
	                return;
	            } else {
	                System.out.println("댓글 작성자가 아닙니다.");
	                return;
	            }
	        }
	    }
	}
	
	@Override
	public void read(Scanner scan) {
		// TODO Auto-generated method stub
		postId = scan.nextInt();
		commentId = scan.nextInt();
		commentWriter = scan.next();
		commentContent = scan.nextLine();
		
	}
	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean matches(String kwd) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
    public String toString() {
		String result = commentWriter +" : "+ commentContent;
        return result;
    }

	public int getCommentId() {
		// TODO Auto-generated method stub
		return this.commentId;
	}

	public String getCommentWriter() {
		// TODO Auto-generated method stub
		return this.commentWriter;
	}

	public int getPostId() {
		// TODO Auto-generated method stub
		return this.postId;
	}
	

}
