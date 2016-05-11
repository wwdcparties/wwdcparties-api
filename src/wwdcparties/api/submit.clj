(ns wwdcparties.api.submit
  (:require [clj-time.core :as t]
            [clj-time.coerce :as tc]
            [clj-time.format :as tf]
            [clojure.string :as s]))

(defn form->party [form]
  {:description (:description form)
   :end_time (form->date (last (form->days (:day form))) (:choice_time_end form))
   :event_url ""
   :excerpt ""
   :location ""
   :meta (form->meta (:meta form))
   :name (:choice_party_name form)
   :slug (name->slug (:choice_party_name form))
   :sponsor_name ""
   :sponsor_url ""
   :start_time (form->date (first (form->days (:day form))) (:choice_time_start form))
   :street_address ""
   :twitter_handle ""
   :type "party"
   :types (form->types (:party-type form))})

(defn form->days [days]
  (if (nil? days) 
      nil
      (let [days (-> days vector flatten vec)]
        (sort (map #(Integer/parseInt (re-find #"\A-?\d+" %)) days)))))

(def formatter (tf/with-zone 
                 (tf/formatter "dd HH:mm MMMM yyyy")
                 (t/time-zone-for-id "America/Los_Angeles")))
(defn form->date [day time]
  (let [date-string (str day " " time " June 2016")
        parsed-date (tf/parse formatter date-string)]
    (tc/to-epoch parsed-date)))

(defn form->meta [form-meta]
  (cond
    (nil? form-meta) {}
    (string? form-meta) {form-meta true}
    :else (zipmap form-meta (repeat true))))

(defn form->types [form-types]
  (if (nil? form-types) 
      []
      (-> form-types vector flatten vec)))

(defn name->slug [name]
  (-> name
      s/lower-case
      (#(s/replace % #"[^A-Za-z\s]" ""))
      (#(s/replace % #"\s" "-"))))
