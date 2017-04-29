package codecheck;

import java.io.*;
import java.util.*;
import java.lang.*;

class Game {
	List<String> left_words;
	String now_word;
	Game(String str,List<String> word_list){
		this.now_word = str;
		this.left_words = word_list;
	}

	String judge(String str){
		for(int i = 0;i < this.left_words.size();i++){
			if(str.equals(this.left_words,get(i))){
				return "OK";
			}
		}
		return "NG";
	}

}

class Player {
	String ai_program_path;

	Player(String str){
		this.ai_program_path = str;
	}
	String answer(){

	}
}

public class App {
	public static void main(String[] args) {
		Player first_player = new Player(args[0]);
		Player second_player = new Player(args[1]);
		Game game = new Game();
		for (int i = 0, l = args.length; i < l; i++) {
			System.out.println(args[i]);
		}
	}
}
