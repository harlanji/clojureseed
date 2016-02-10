(ns harlanji.clojureseed.web.core
  (:require [com.stuartsierra.component :as component]
            [cljs.core.async :refer [<! >! put! chan] :as async]
            [taoensso.sente  :as sente :refer [cb-success?]]
            )
  (:require-macros [cljs.core.async.macros :refer [go go-loop] :as asyncm]))

(.info js/console "Prod loaded.")

(defrecord App [sente]
  component/Lifecycle
  (start [component]
    (.info js/console "App started. :) :) sente=" sente)
    component)
  (stop [component]
    (.info js/console "App stopped.")
    component))

(defprotocol Logger
  (info [c msg args])
  (error [c msg args])
  (debug [c msg args]))

(defn- send-log! [c event msg args]
  (let [send-fn! (get-in c [:sente-chsk :send-fn])]
    (.info js/console (str event ": " msg))
    (send-fn! [event {:log/msg msg
                      :log/args args}])))


(defrecord PubSub []
  component/Lifecycle
  (start [c]
    (comment let [local-pub (chan)
          local-pubsub {:pub local-pub
                        :subs (subs pub)}
          dispatch (comment fn [event]
                     (when (remote-dispatch? event))
                     )])
    c)
  (stop [c] c))

(defrecord Sente [env
                  sente-chsk]
  component/Lifecycle
  (start [c]
    (let [sente-chsk (sente/make-channel-socket! "/chsk" {:type :auto
                                                          :host "localhost:3000" ; FIXME only in figwheel
                                                          :client-id (:client-id env)})
          c (assoc c :sente-chsk sente-chsk)]
      (go (while true
            (let [event (<! (:ch-recv sente-chsk))]
              (.info js/console (str "Got event! " (:id event))))))
      (.info js/console "Sente started. client-id=" (:client-id env))

      (.setTimeout js/window #((:send-fn sente-chsk) [:client/hi]) 500)
      (.setTimeout js/window #(info c "HI!!!!!" ["From a timer"]) 500)

      c))
  (stop [c]
    (when (:sente-chsk c)
      (.info js/console "Sente stopped.")
      (dissoc c :sente-chsk)))

  Logger
  (info [c msg args] (send-log! c ::log.info msg args))
  (error [c msg args] (send-log! c ::log.error msg args))
  (debug [c msg args] (send-log! c ::log.debug msg args)))


(defrecord Env [client-id])

(defonce system (atom (component/system-map :env (map->Env {:client-id "1234"})
                                            :sente (component/using
                                                     (map->Sente {})
                                                     [:env])
                                            :pubsub (map->PubSub {})
                                            :app (component/using
                                                   (map->App {})
                                                   {:env :env
                                                    :sente :sente
                                                    :pubsub :pubsub}))))

(defn send-message [msg]
  (let [sente-chsk (get-in @system [:sente :sente-chsk])]
    ))

(defn start! [window]
  (.info js/console "Starting...")
  (swap! system component/start)
  (.info js.console "Finished starting."))