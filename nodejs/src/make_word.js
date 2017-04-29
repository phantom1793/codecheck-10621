var client = require('cheerio-httpcli');
var fs = require('fs');
var URL = "http://tokoton-eitango.com/eitango/hinshiindex/1/";
var file_path = "../../data/words.csv";
var max_path = 64;
var words = [];
var promises = [];

//英単語リストをスクレイピングする。サーバエラーが出てもリダイレクトを続ける
function get_words(url,redirect){
  return new Promise(function(resolve,reject){
    var max_redirect = 20;
    var temp_words = [];
    if(redirect == max_redirect){
      reject("NG");
    }
    client.fetch(url,{},function(err,$,res){
      if(err){
        setTimeout(function(){
          get_words(url,redirect+1);
        },3000);
      }
      $(".news_item").children("table").find("a").each(function(idx){
        var word = $(this).text();
        if(temp_words.indexOf(word) == -1){
          temp_words.push(word);
        }
      });
      resolve(temp_words);
    });
  });

}

//英単語配列から英語をcsvファイルに入れる
function make_words_file(arr){
  arr.sort();
  var body = "";
  for(var i = 0; i < arr.length;i++){
    body += arr[i]+",";
  }
  fs.writeFileSync(file_path,body,"utf8");
}

for (var i = 0;i <= max_path;i++){
  promises.push(get_words(URL+i,0));
}
//スクレイピングする非同期処理軍をまとめてcsvファイルに書き込む
Promise.all(promises).then(function(item){
  for(var i = 0; i < item.length;i++){
    for(var j = 0; j < item[i].length;j++){
      if(words.indexOf(item[i][j]) == -1){
        words.push(item[i][j]);
      }
    }
  }
  make_words_file(words);
},function(){
  console.log("reject");
});
