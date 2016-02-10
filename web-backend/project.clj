(defproject harlanji.clojureseed/web-backend "0.0.1-SNAPSHOT"
            :dependencies [[org.clojure/clojure :clj1.8]
                           [ring "1.4.0"]
                           [ring/ring-anti-forgery "1.0.0"]
                           [harlanji.clojureseed/core _]
                           [harlanji.clojureseed/web-client _]
                           [http-kit "2.1.18"]
                           [environ "1.0.2"]
                           [com.taoensso/sente :sente1.7]]
            :plugins [[lein-modules "0.3.11"]]

            :main harlanji.clojureseed.web.main

            :profiles {:uberjar {:aot :all}})