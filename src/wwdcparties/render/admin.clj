(ns wwdcparties.render.admin
  (:require [wwdcparties.api.db :as db]))

(defn party-row [party]
  [:tr [:td 
        [:a {:href (str "/parties/" (:slug party))} (:name party)]]])

(defn page [user]
  (let [parties (db/submitted user)]
    [:table
     [:thead [:tr [:th "Party Name"]]]
     [:tbody (map party-row parties)]]))
