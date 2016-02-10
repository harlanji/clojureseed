(ns charlanji.lojureseed.lifecycle
    (:require [com.stuartsierra.component :as component]
              [harlanji.clojureseed/core :refer [upon event]]))

(defn- start* [component]
       (upon component (event :component/start)))

(defn- stop* [component]
       (upon component (event :component/stop)))


(defn start [component]
      (if (is? )
        (component/start component)
        (component/update-system component start*)))
(defn stop [component]
      (if (is? )
        (component/stop component)
        (component/update-system-reverse component stop*)))