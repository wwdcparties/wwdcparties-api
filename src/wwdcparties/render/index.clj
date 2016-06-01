(ns wwdcparties.render.index
  (:require [clj-time.core :as t]
            [clj-time.format :as ft]
            [inflections.core :as inf]
            [wwdcparties.render.colors :as colors]
            [wwdcparties.render.core :refer :all]
            [wwdcparties.render.nav :as nav]
            [wwdcparties.render.filters :as filters]
            [wwdcparties.model.party :as party]
            [wwdcparties.render.footer :as footer]
            [wwdcparties.render.icons :as icons]
            [wwdcparties.render.scripts :as scripts]))

(def date-formatter (ft/formatter "EEEE, MMMM " party/pacific))
(def start-formatter (ft/formatter "h:mm a" party/pacific))
(def end-formatter (ft/formatter "h:mm a" party/pacific))

(defn day-header [date]
  (str (ft/unparse date-formatter date)
       (inf/ordinalize (t/day date))))

(defn date [party]
  (let [date (party/start party)]
    (str (ft/unparse date-formatter date)
         (inf/ordinalize (t/day date)))))

(defn formatted-start [party]
  (ft/unparse start-formatter (party/start party)))

(defn formatted-end [party]
  (ft/unparse end-formatter (party/end party)))

(defn days [parties]
  (partition-by (fn [party] (t/day (party/start party))) parties))

(defn includes-type [type party]
  (boolean (some #(= type %) (:types party))))

(defn party-types [party]
  (list
   (if (includes-type "party" party)
     ["Party"])
   (if (includes-type "meetup" party)
     icons/chat)
   (if (includes-type "presentation" party)
     icons/presentation)
   (if (includes-type "outdoors" party)
     icons/tree)

   (if (:18+ (:meta party))
     [:span.age "18+"])
   (if (:21+ (:meta party))
     [:span.age "21+"])
   (if (:booze (:meta party))
     icons/booze)
   (if (:food (:meta party))
     icons/food)
   (if (:ticket (:meta party))
     icons/ticket)))

(defn party-classes [party]
  (str
   (if (includes-type "party" party) "party ")
   (if (includes-type "meetup" party) "meetup ")
   (if (includes-type "presentation" party) "presentation ")
   (if (includes-type "outdoors" party) "outdoors ")
   (if (:18+ (:meta party)) "18-plus ")
   (if (:21+ (:meta party)) "21-plus ")
   (if (not (or
             (:18+ (:meta party))
             (:21+ (:meta party))))
     "all-ages ")
   (if (:booze (:meta party)) "alcohol ")
   (if (:food (:meta party)) "food ")
   (if (:ticket (:meta party)) "ticket ")))

(defn party-listing [party]
  [:li..event-listing {:class (party-classes party)}
   [:a.event-link {:href (str "/parties/" (:slug party))}
    [:span.event__time (formatted-start party) " to " (formatted-end party)]
    [:div.party-info
     [:div.party-excerpt
      [:h3.event__title (:name party)]
      [:p (:excerpt party)]]
     [:div.event-type (party-types party)]]]])

(defn module-day [day-parties]
  (let [day (party/start (first day-parties))]
       [:div.day {:id (colors/day day)}
        [:h2.day-title (day-header day)]
        [:ul.events (map party-listing day-parties)]]))

(defn container [parties]
  [:div#parties
   filters/filter-buttons
   [:div.section-events {:role "main" } (map module-day (days parties))]])

(defn page [parties]
  (list
   [:head shared-head
    [:title "WWDC Parties 2016"]]
   [:body
    nav/header
    (container parties)
    footer/footer
    scripts/scripts]))
