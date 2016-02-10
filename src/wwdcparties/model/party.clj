(ns wwdcparties.model.party
  (:require [icalendar.event]
            [clj-time.core :as t]
            [clj-time.coerce :as ct])
  (:import [icalendar.event Event]))

(def pacific (t/time-zone-for-id "America/Los_Angeles"))

(defrecord Party [description slug meta name
                  start_time excerpt sponsor_url
                  types end_time shortcode event_url
                  street_address sponsor_name location
                  twitter_handle])

(defn from-json [json]
  (map->Party json))

(defn epoch->DateTime [epoch]
  (ct/from-long (* 1000 epoch))) ; clj-time uses millis

(defn start [party]
  (t/to-time-zone (epoch->DateTime (.start_time party)) pacific))

(defn end [party]
  (t/to-time-zone (epoch->DateTime (.end_time party)) pacific))

(defn section [party]
  (let [start-hour (t/hour (start party))]
    (cond (< start-hour 12) :morning
          (and (>= start-hour 12)
               (< start-hour 17)) :afternoon
          (>= start-hour 17) :evening)))

(defn event [party]
  (Event.
   (start party)
   (.slug party)
   (start party)
   (end party)
   (:name party)))
