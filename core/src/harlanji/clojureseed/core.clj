(ns harlanji.clojureseed.core)

(def event-key :component/event)

(defmacro defrecord+upon
          "A shortcut to defining component records. Automatically creates a dispatcher that dispatches by
           :command/event field of the single argument."
          [event & body]
          (let [react-symbol (symbol (str *ns*) "upon")
                react-multi `(defrecord ~react-symbol []
                                        com.stuartsierra.component/Lifecycle
                                        (start [component])
                                        (stop [component]))]
               `(do
                  (when-not ~(find-var react-symbol)
                            (println "Defining default react dispatcher: ")
                            ~react-multi)
                  (defmethod ~react-symbol ~event [~'component ~'event]
                             ~@body))))

(comment defn defupon-map
  "A shortcut to defining multi-methods. Automatically creates a dispatcher that dispatches by
   :command/event field of the single argument."
  [event body]
  (let [react-symbol (symbol (str *ns*) "upon")
        react-multi '(defmulti ~react-symbol #(get %2 event-key))]
       (do
          (when-not (find-var react-symbol)
                    (println "Defining default react dispatcher:" react-multi)
                    (eval react-multi))
          (defmethod react-symbol event '[component event]
                 (body component event)))))

(defn event
  "Shortcut methods to create an event."
  ([event-name]
    {event-key event-name})
  ([event-name m]
    (merge m (event event-name)))
  ([event-name k v & kvs]
    (event event-name (apply hash-map k v kvs))))


(comment "test:"
  (let [say-reactor '(defupon :request-say
                              (format "say with component=%s, event=%s" component event))]
       (println (macroexpand-1 say-reactor))
       (eval say-reactor))

  (let [component {:name :my.component}
        say-hi (event :request-say :message "hi")]
       (upon component say-hi))
 )