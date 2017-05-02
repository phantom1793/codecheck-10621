## 開発物一覧
### Unityで開発した3Dインターフェースを持つ乗換案内サービス
- URL
  - https://itunes.apple.com/se/app/patrash3d/id1086503245?mt=8
- 工夫点
  - 検索システムは研究室のサーバに任せ、httpでデータの送受信を行ったこと(検索の仕組みそのものは自分で作ってない)
  - 受け取ったデータを整理し、検索画面や便の情報などを一つのオブジェクトの側面に表示させたこと。オブジェクト回転で検索を行える

### Node.jsで上記の乗換案内サービスを作り変えた
- URL
  - http://patrash.ait.kyushu-u.ac.jp:8080
- 補足
  - 九州大学伊都キャンパス案内アプリ(http://itocamlife.go1.jp/itocamlife/)の付加サービスとして開発した
- 工夫点
  - 検索フォームが検索結果と同じ画面にあるため、いちいち画面を遷移しなくて済む（デザインはjQueryMobile)
  - 便情報をタッチすると、便から出発地点、到着地点を抜き出しフォームに自動で入力すること

### Heroku上にNode.jsで作ったスケジュール管理アプリ
- URL
  - http://my-schedule-114514.herokuapp.com/
- 補足
  - データベースはmLab(mongoDBのオンラインストレージ)
  - ブラウザによって挙動が変わる(特にSafari,IEは知らない)
- 工夫点
  - いろんなフレームワーク、ミドルウェアを試したこと(heroku,Express,jquery,materialize)
