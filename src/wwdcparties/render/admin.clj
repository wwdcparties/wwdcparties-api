(ns wwdcparties.render.admin
  (:require [wwdcparties.api-handler :as api]))

(defn party-row [party]
  [:tr [:td 
        [:a {:href (str "/parties/" (:slug party))} (:name party)]]])

(defn page [user]
  (let [parties (api/submitted user)]
    [:table
     [:thead [:tr [:th "Party Name"]]]
     [:tbody (map party-row parties)]]))
