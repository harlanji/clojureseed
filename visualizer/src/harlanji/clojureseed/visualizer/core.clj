(ns visualizer.core
    (:require [quil.core :as q]
      [quil.middleware :as m]
      [com.stuartsierra.component :as component]))

(declare setup
         update-state
         draw-state)

(defrecord VisualizerUI [sketch]
           component/Lifecycle
           (start [component]
            (update component :sketch #(or % (q/defsketch visualizer
                                               :title "You spin my circle right round"
                                               :size [500 500]
                                               ; setup function called only once, during sketch initialization.
                                               :setup setup
                                               ; update-state is called on each iteration before draw-state.
                                               :update update-state
                                               :draw draw-state
                                               :features [:keep-on-top]
                                               ; This sketch uses functional-mode middleware.
                                               ; Check quil wiki for more info about middlewares and particularly
                                               ; fun-mode.
                                               :middleware [m/fun-mode]))))
           (stop [component] component))


(defrecord VisualizerUI [sketch]
  component/Lifecycle
  (start [component]
    (update component :sketch #(or % (q/defsketch visualizer
                                                  :title "You spin my circle right round"
                                                  :size [500 500]
                                                  ; setup function called only once, during sketch initialization.
                                                  :setup setup
                                                  ; update-state is called on each iteration before draw-state.
                                                  :update (partial update component)
                                                  :draw draw-state
                                                  :features [:keep-on-top]
                                                  ; This sketch uses functional-mode middleware.
                                                  ; Check quil wiki for more info about middlewares and particularly
                                                  ; fun-mode.
                                                  :middleware [m/fun-mode]))))
  (stop [component] component)


  )





(defn setup []
      ; Set frame rate to 30 frames per second.
      (q/frame-rate 30)
      ; Set color mode to HSB (HSV) instead of default RGB.
      (q/color-mode :hsb)
      ; setup function returns initial state. It contains
      ; circle color and position.
      {:color1 0
       :angle1 0
       :color2 0
       :angle2 0})

(defn update-state [state]
  ; Update sketch state by changing circle color and position.
  (let [state {:color1 (mod (+ (:color1 state) 0.7) 255)
               :angle1 (+ (:angle1 state) 0.1)
               :color2 (mod (+ (:color2 state) 0.7) 255)
               :angle2 (- (:angle2 state) 0.2)}]
       state))

(defn draw-state [state]
      ; Clear the sketch by filling it with light-grey color.
      (q/background 240)
      ; Set circle color.
      (q/fill (:color1 state) 255 255)
      ;(q/fill (:color2 state) 255 255)
      ; Calculate x and y coordinates of the circle.
      (let [angle1 (:angle1 state)
            angle2 (:angle2 state)
            x1 (* 150 (q/cos angle1))
            y1 (* 150 (q/sin angle1))
            x2 (* 150 (q/cos angle2))
            y2 (* 150 (q/sin angle2))]
           ; Move origin point to the center of the sketch.
           (q/with-translation [(/ (q/width) 2)
                                (/ (q/height) 2)]
                               ; Draw the circle.
                               (q/ellipse x1 y1 100 100)
                               (q/ellipse x2 y2 100 100))))


(defonce system (atom (component/system-map :visualizer-ui (map->VisualizerUI {}))))

(defn main- [args]
  (swap! system component/start))