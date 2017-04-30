package codecheck;

import java.io.*;
import java.util.*;
class AI{
	//知っている単語集
	List<String> knowladge = new ArrayList<String>();
	//代替データにする
	void get_alternative_words(){
		this.knowladge = new ArrayList<String>(Arrays.asList("apple","abacus","aim","accountant","acid","actor","air","age","agriculture","amateur",
		"barn","bank","basis","bath","baby","beetle","beauty","benefit","bed","bill",
		"cat","camel","cancer","castel","carpenter","century","chair","chamber","chaos","charm",
		"dealings","daughter","dailect","danger","dawn","daylight","death","debt","deceit","decision",
		"earth","ear","earlings","earnest","effort","eggplant","economy","evidence",
		"faith","fake","fame","familiar","family","famine","fanatic","fantasy","fare","farewell","farm","farmhouse","fashion",
		"governor","grace","grade","gradient","graduate","graduation","graft","grain","grant","grasp","grass",
		"hall","hamper","hand","handle","handshake","harbor","hardship","hare","harm","harvest",
		"illegitimate","illiterate","illness","illusion","imaginary","imbalance","immediacy","immersion",
		"jail","jam","jar","jargon","jaw","jealousy","jellyfish","jerk","jewel",
		"kerosene","key","kidnap","kill","kin","kind","kindergarten","kingdom","kinship",
		"lighthouse","lightning","likelihood","likeness","limb","limitation","line","linen","liquid","liquor","literal","literate","literature","litigation","liver",
		"maximum","mayor","maze","meadow","meal","mean","meaning","means","meantime","measles","meat","mechanism","medicine","medium","melancholy","memory","menace",
		"nail","nap","narrative","nation","nature","navigation","navy","necessary","necessity","need","needle",
		"oath","object","objection","objective","objectivity","obligation","oblivion","obscurity","observance","observation","obsession","obstacle","obstruction",
		"parent","pariah","park","parliament","particle","partition","pass","passage","passion","pastime","pastor","pasture","patch","patent","path","pathway","paradox",
		"quail","qualification","quality","quandary","quantity","quantum","quarrel","quarter","quest","question","questionnaire",
		"rice","riddle","riot","ritual","rival","rivalry","river","rock","role","room","root","rout","routine","row","royalty","rubbish",
		"snag","snare","snow","socialist","society","sociology","strategy","stratum","straw","streak","stream","strength","strike",
		"tendency","term","termination","terms","terrace","troop","trouble","troupe","trousers","trunk","trust","truth",
		"ulcer","uncle","undesirable","unease","unemployment","uniform","uniformity","union","unity","universe","upbringing","uproar",
		"valley","value","vandalism","vanity","vapor","vegetable","vegetarian","vehicle","vein","voice","volcano","voltage","victor","view","viewpoint","vigor","village",
		"wage","wages","wall","wallet","want","war","wheel","whim","whip","whirlwind","whore","width","wilderness","wind",
		"xenophobia","xanadu","xenon",
		"yard","yarn","yesterday","yield","yolk","youngster","youth",
		"zeal","zenith","zest","zinc"));
	}
	//英単語データを読み込む
	void get_words(){
		try{
			BufferedReader br = new BufferedReader(new FileReader("data/words.csv"));
			String str;
			//csvファイルを読み込む(一行になっている)
			str = br.readLine();
			//strs = str.split(",");
			StringTokenizer tokenizer = new StringTokenizer(str,",");
			while(tokenizer.hasMoreTokens()){
				this.knowladge.add(tokenizer.nextToken());
			}
		} catch(FileNotFoundException e){
			System.out.println(e);
			//System.out.println("代替データを使用します。");
			this.get_alternative_words();
		} catch(IOException e){
			System.out.println(e);
			//System.out.println("代替データを使用します。");
			this.get_alternative_words();
		}
	}
	//英単語群から開始後からなるものを集める
	List<String> get_s_words_list(char s,List<String> list){
		List<String> temp_list = new ArrayList<String>();
		for(int i = 0;i < list.size();i++){
			if(s == list.get(i).charAt(0)){
				temp_list.add(list.get(i));
			}
		}
		return temp_list;
	}
	//ある単語の末尾後からなるリスト内の単語数を求める
	int get_match_words_count(String str,List<String> list){

		char s = str.charAt(str.length() - 1);
		//自分自身のカウントを避ける
		List<String> temp_list = new ArrayList<String>(list);
		for(int i = 0;i < temp_list.size();i++){
			if(str.equals(temp_list.get(i))){
				temp_list.remove(i);
				break;
			}
		}
		int len = this.get_s_words_list(s,temp_list).size();
		return len;
	}
	//作戦その１:末尾後から始まる単語がないものを選ぶ
	String strategy1_get_word(List<String> enable_words,List<String> list){
		for(int i = 0;i < enable_words.size();i++){
			String temp_str = enable_words.get(i);
			if(this.get_match_words_count(temp_str,list) == 0){
				return temp_str;
			}
		}
		return "";
	}
	//作戦その２:末尾後から始まる単語群のそれぞれの末尾語から始まる単語数が1以上のものを選ぶ
	String strategy2_get_word(List<String> enable_words,List<String> list){
		for(int i = 0;i < enable_words.size();i++){
			//末尾後から始まる単語軍を取得
			char s = enable_words.get(i).charAt(enable_words.get(i).length() - 1);
			List<String> end_word_list = this.get_s_words_list(s,list);
			Boolean word_OK = true;
			for(int j = 0;j < end_word_list.size();j++){
				if(this.get_match_words_count(end_word_list.get(j),list) == 0){
					word_OK = false;
					break;
				}
			}
			if(word_OK){
				return enable_words.get(i);
			}
		}
		return "";
	}
	//末尾語からなる適当な単語を返す
	String random_answer(char s){
		this.get_words();
		List<String> s_knowladge = this.get_s_words_list(s,this.knowladge);
		//末尾後からなる単語がない場合nullを返す
		if(s_knowladge.size() == 0){
			return null;
		} else{
			long seed = System.currentTimeMillis();
			Random rnd = new Random(seed);
			int ran = rnd.nextInt(s_knowladge.size());
			return s_knowladge.get(ran);
		}
	}
	//末尾語からなる英単語を残りのリストから答える
	String get_answer(String str,List<String> left_words){
		char s = str.charAt(str.length() - 1);
		String answer;
		//末尾語からなる単語を残りの単語から集める
		List<String> enable_words = this.get_s_words_list(s,left_words);
		//末尾語から始まる単語がない場合、知っている単語から末尾語から始まるものを適当に選ぶ(負け)
		if(enable_words.size() == 0){
			return this.random_answer(s);
		} else {
			//作戦1
			if(!this.strategy1_get_word(enable_words,left_words).equals("")){
				//System.out.println("strategy1");
				return this.strategy1_get_word(enable_words,left_words);
			}
			//作戦2
			if(!this.strategy2_get_word(enable_words,left_words).equals("")){
				//System.out.println("strategy2");
				return this.strategy2_get_word(enable_words,left_words);
			}
			//作戦3:適当に選ぶ
			//System.out.println("strategy3 start");
			long seed = System.currentTimeMillis();
			Random rnd = new Random(seed);
			int ran = rnd.nextInt(enable_words.size());
			return enable_words.get(ran);
		}
	}
}

public class App {
	public static void main(String[] args) {
		AI ai = new AI();
		String first_word = args[0];
		List<String> words_list = new ArrayList<String>(Arrays.asList(args));
		words_list.remove(0);
		String answer = ai.get_answer(first_word,words_list);
		System.out.println(answer);
	}
}
