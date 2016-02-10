(defproject harlanji.clojureseed/core "0.0.1-SNAPSHOT"
  :plugins [[lein-modules "0.3.11"]]
  :dependencies [[org.clojure/clojure :clj1.7 :scope "provided"] ; allow platform to proide its own version of clojure.
                 [com.stuartsierra/component :component/clojure1.2+ :scope "provided"]
                 [org.clojure/core.async "0.2.374" :scope "provided"]]
            :profiles {:provided {:dependencies [[com.stuartsierra/component :component/clojure1.2+]
                                                 [org.clojure/clojure :clj1.7]
                                                 [org.clojure/core.async "0.2.374"]]}}
  )