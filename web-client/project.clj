(defproject harlanji.clojureseed/web-client "0.0.1-SNAPSHOT"
            :dependencies [[org.clojure/clojure :clj1.8]
                           [org.clojure/clojurescript :cljs1.8]
                           [com.stuartsierra/component :component/clojure1.7+]
                           [com.taoensso/sente :sente1.7]

                           ;[reagent "0.5.1"
                           ; :exclusions [org.clojure/tools.reader]]
                           ]

            :plugins [[lein-modules "0.3.11"]
                      [lein-environ "1.0.2"]
                      [lein-cljsbuild "1.1.2"]]

            ;

            :hooks [leiningen.cljsbuild]


            ; :prep-tasks ["compile" ["cljsbuild" "once"]]

            :cljsbuild  {:builds {:prod {:source-paths ["src"]
                                  :compiler     {:main       "harlanji.clojureseed.web.core"
                                                  :output-to  "resources/public/gen/js/main.js"
                                                  :output-dir "resources/public/gen/js"
                                                  :asset-path "gen/js"}}}}


            ; opinion: two options: 1) start with figwheel for dev. 2) start with main entry point.

            :clean-targets ^{:protect false} [:target-path "resources/public/gen"]

            ; opinion: dev should be a layer on top of prod. system-map can be updated by dev accordingly, before (start)
            :profiles {:dev {:plugins [[lein-figwheel "0.5.0-4"]]
                             :dependencies [;;;[com.analogzen.clojure-stack/web-backend _]
                                            ]
                             :cljsbuild {:builds {:dev {:source-paths ["src" "dev-src"]

                                                        ; if only we could copy these from core...
                                                        :compiler {:main "harlanji.clojureseed.web.dev"
                                                                   :output-to "resources/public/gen/js/main.js"
                                                                   :output-dir "resources/public/gen/js"
                                                                   :asset-path "gen/js"}



                                                        ; it's nice to show all the options. editor option could be a projection of defaults,
                                                        ; from getting effective project model.


                                                        :figwheel {:server-ip "127.0.0.1"
                                                                   :server-port 3449
                                                                   :server-logfile "figwheel_server.log"
                                                                   :http-server-root "public"
                                                                   :heads-up-display true
                                                                   :repl true
                                                                   :nrepl-port 7888
                                                                   :css-dirs ["resources/public/css"]
                                                                   :on-jsload harlanji.clojureseed.web.dev/reload!
                                                                   ;;; :ring-init example.dev/init! -- no such option
                                                                   ;;;:ring-handler example.core/app
                                                                   }}}}}}
            :min-lein-version "2.5.0")