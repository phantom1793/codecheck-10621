package codecheck;

import java.io.*;
import java.lang.*;
import java.util.*;

class Game {
	List<String> left_words;
	String now_word;
	Game(String str,List<String> word_list){
		this.now_word = str;
		this.left_words = word_list;
	}
	Boolean judge(Player player,String str){
		for(int i = 0;i < this.left_words.size();i++){
			if(str.equals(this.left_words.get(i))){
				System.out.println(player.name+" (OK): "+str);
				this.now_word = str;
				this.left_words.remove(this.left_words.indexOf(str));
				return true;
			}
		}
		System.out.println(player.name+" (NG): "+str);
		if(player.name.equals("FIRST"))
			System.out.println("WIN - SECOND");
		else
			System.out.println("WIN - FIRST");
		return false;
	}
	void game_start(Player first,Player second){
		while(true){
			if(this.judge(first,first.answer()) == false)
				break;
			if(this.judge(second,second.answer()) == false)
				break;
		}
	}
}

class Player {
	String ai_program_path;
	String name;
	Game game;
	Player(String str1,String str2,Game game){
		this.ai_program_path = str1;
		this.name = str2;
		this.game = game;
	}
	String answer(){
		String command = "";
		command += this.ai_program_path + " " + game.now_word;
		for(int i = 0;i < game.left_words.size();i++){
			command += " " + game.left_words.get(i);
		}
		try{
			Runtime runtime = Runtime.getRuntime();
			Process p = runtime.exec(command);
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String result = br.readLine();
			return result;
		} catch (IOException e){
			System.out.println(e);
		}
			return "";
	}
}

public class App {
	public static void main(String[] args) {
		List<String> left_words = new ArrayList<String>();
		if(args.length < 3){
			System.out.println("引数の数が足りません");
			return;
		}
		for(int i = 3;i < args.length;i++){
			left_words.add(args[i]);
		}
		Game game = new Game(args[2],left_words);
		Player first_player = new Player(args[0],"FIRST",game);
		Player second_player = new Player(args[1],"SECOND",game);


		game.game_start(first_player,second_player);


	}
}
