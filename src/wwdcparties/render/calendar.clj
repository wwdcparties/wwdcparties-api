(ns wwdcparties.render.calendar
  (:require [wwdcparties.model.party :as party]
            [icalendar.core :as cal]
            [icalendar.event :as event]))

(defn calendar [parties]
  (cal/calendar "WWDC Parties" (map party/event parties)))
