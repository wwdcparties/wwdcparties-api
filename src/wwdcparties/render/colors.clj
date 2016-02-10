(ns wwdcparties.render.colors
  (:require [clj-time.format :as ft]
            [wwdcparties.model.party :as party]))

(def day-formatter (ft/formatter "EEEE" party/pacific))
(def color-map {:sunday "9620C6"
                :monday "317DFC"
                :tuesday "E5C439"
                :wednesday "EA4324"
                :thursday "CF2652"
                :friday "8FB647"
                :saturday "30ADA2"})

(defn day [date]
  (clojure.string/lower-case (ft/unparse day-formatter date)))

(defn tint [party]
  (day (party/start party)))


(defn color [party]
  ((keyword (day (party/start party))) color-map))
