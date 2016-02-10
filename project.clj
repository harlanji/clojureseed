(defproject harlanji.clojureseed "0.0.1-SNAPSHOT"
  :description "A demo PubSub microservice."

  :plugins [[lein-modules "0.3.11"]
            [lein-codox "0.9.1"]
            [lein-ancient "0.6.8"]]

  ;:profiles {:clj {}
  ;           :cljs {}
  ;           :objcbuild {}
  ;           :droid {}
  ;           :fruit {}
  ;           }
  ;:aliases {"all" []}


  :modules {:dirs ["core"
                   "web-client"
                   "web-backend"
                   "ios-client"
                   ;"visualizer"
                   ]


            ; todo: clojure profiles for clojure environments. baseline, cljs, clj, objcbuild, fruit, droid, etc.


            :versions {harlanji.clojureseed/core "0.0.1-SNAPSHOT"
                       harlanji.clojureseed/web-client "0.0.1-SNAPSHOT"
                       harlanji.clojureseed/web-backend "0.0.1-SNAPSHOT"


                       ; idea: automatically insert module versions
                       ; https://github.com/jcrossley3/lein-modules/issues/29
                       lein-objcbuild "0.1.10"
                       lein-cljsbuild "1.1.2"
                       clj-kafka "0.3.4"
                       :clj1.7 "1.7.0"
                       :cljs1.7 "1.7.170"
                       :clj1.8 "1.8.0"
                       :cljs1.8 "1.7.228"
                       :clj-objc1.7 "1.7.0-RC1" ; even if stuff is beta2 it should be okay, since it's not AOT'd
                       :component/clojure1.2+ "0.2.4-harlanji-SNAPSHOT" ; ios, until [galdolber/clojure-objc "1.7.0"]
                       :component/clojure1.7+ "0.3.1" ; other
                       :sente1.7 "1.7.0"
                       reagent "0.5.1"
                       speclj "3.3.1"}
            }
  ; todo lein-modules should automate this if lein-codox is here. (or vice, versa).




  :codox {:source-paths ["/web-client/src"
                    "/web-client/dev-src"
                    "/web-backend/src"
                    ;"visualizer/src"
                    ;"pubsub-service/src"
                    ;"ios-client/src"
                    ;"core/src"
                         ]
          ;:namespaces [#"^example\."]
          :doc-paths ["doc"]
          :metadata {:doc "FIXME: write docs"}
          :language :clojure
          }

  :profiles {:clj {}
             :cljs {:codox {:language :clojurescript}}}

  )