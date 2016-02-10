(ns harlanji.clojureseed.ios.core
    (:require [uikit.core :as uikit]
              [com.stuartsierra.component :as component]
              [harlanji.clojureseed.pubsub :as pubsub]
              ;[clojure.core :refer [not]]
              ;[clojure.core.async :as a]
              )
  (:import [java.net URL]))

(def white (-> ($ UIColor)
               ($ :whiteColor)))

(declare abc say-hi)

(defn on-tap [ps-local]
  (fn [scope]
      (println "Tap" ps-local)
      (pubsub/pub! ps-local [::on-tap])
      (say-hi)))

(defn- my-screen [g ps-local] [:UIView :main
                {:setBackgroundColor white
                 :constraints        ["C:button.centerx=main.centerx"
                                      "C:button.centery=main.centery"]}
                [(uikit/button 1) :button {:setTitle:forState [g 0]
                                           :gestures          {:UITapGestureRecognizer (#'on-tap ps-local)}}]])

(defn say-hi []
  (let [c (.openConnection (URL. "http://localhost:3000/hi"))]
    (println "hi: :)" (slurp (.getContent c)))))

(defrecord MainUI [ps-local
                   on-tap-sub
                   ui]
           component/Lifecycle
           (start [component]
             (println "main-ui ps-local=" ps-local)
                  (let [window (-> ($ UIWindow)
                                   ($ :alloc)
                                   ($ :initWithFrame (-> ($ UIScreen)
                                                         ($ :mainScreen)
                                                         ($ :bounds))))
                        nav (-> ($ UINavigationController)
                                ($ :new))

                        on-tap-sub (comment pubsub/sub ps-local ::on-tap)]
                       ;(println greeting)
                       (pubsub/sub ps-local (fn [sub-id event]
                                       (println "event!" event)))

                       (doto window
                             ($ :makeKeyAndVisible)
                             ($ :setRootViewController nav)
                             ($ :setBackgroundColor white))
                       (reset! uikit/current-top-controller nav)
                       ($ nav :setNavigationBarHidden true)

                       (assoc component
                         :on-tap-sub on-tap-sub
                              ; FIXME replacing the greeting call with a string works.
                              :ui (uikit/nav-push (uikit/controller "Screen title" (my-screen "Hey hey!" ps-local))))))
           (stop [component]
                 ))




(defonce system (atom (component/system-map
                        ;:pubsub (pubsub/map->LocalDispatcher {})
                        :pubsub (pubsub/map->FuturePubsub {})
                        :ui (component/using (map->MainUI {})
                                             {:ps-local :pubsub
                                              }
                                             ))))



(defn main []
  (println "hi!")
  ;(println "Hai! hello:" (c/hello-again))
  ;@(future (println "hi from the future!"))

  ;(let [{:keys [ui]} (swap! system component/start)]
  ;  ui)
)