
(1)
【動作確認環境】
OS:Windows10 64bit
IDE:Eclipse 2020 Ultimate or Java (https://mergedoc.osdn.jp/)
DataBase:PostgreSQL 11.10(https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)
Webブラウザ:Google Chrome, MicroSoft Edge

【ディレクトリ構成】
mrs_
   |--app アプリケーション
   |--sql DDLファイル

(2)
上記バージョンのEclipseをダウンロードし、
C:\
に解凍する。

(3)PostgreSQL 11.10を
ユーザー名:postgres
パスワード:postgres
でインストールし、(1)のDDLを実行する。

(4)mrsワークスペースを(2)のディレクトリの直下に配置する


(5)Eclipseの起動後、デフォルトの../mrsを指定する

(6)Eclipseのメニューのウィンドウ→ビューの表示→その他→サーバー
を選択し、
Tomcat v9.0サーバー
を選択し、次へを押下、app(mrs)を追加する

(7)Tomcatサーバー起動後、(1)のWebブラウザで
http://localhost:8080/mrs/
と入力し、アプリを起動する。