(defproject harlanji.clojureseed/ios-client "0.0.1-SNAPSHOT"
  :description ""
  :plugins [[lein-modules "0.3.11"]
            [lein-objcbuild "0.1.10"]]
  :objcbuild {:archs [:i386 :x86_64 #_:armv7 #_:armv7s]
              :frameworks [:UIKit :Foundation]}
  :aot :all
  :dependencies [[uikit "0.1.5"]
                 [galdolber/clojure-objc :clj-objc1.7]
                 ;[org.clojure/tools.reader ]
                 [com.stuartsierra/component :component/clojure1.2+]
                 [harlanji.clojureseed/core _]
                 ])