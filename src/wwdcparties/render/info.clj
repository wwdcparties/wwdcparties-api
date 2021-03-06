(ns wwdcparties.render.info
  (:require [cemerick.url :refer [url-encode]]
            [clj-time.core :as t]
            [clj-time.format :as ft]
            [clojure.string :refer [blank?]]
            [inflections.core :as inf]
            [wwdcparties.render.mapbox :as map]
            [wwdcparties.model.party :as party]
            [wwdcparties.render.nav :as nav]
            [wwdcparties.render.core :refer :all]
            [wwdcparties.render.colors :as colors]
            [wwdcparties.render.footer :as footer]
            [wwdcparties.render.icons :as icons]
            [wwdcparties.render.scripts :as scripts]
            [wwdcparties.render.twitter :as twitter]))

(def date-formatter (ft/formatter "EEEE, MMMM " party/pacific))
(def time-formatter (ft/formatter "h:mm a" party/pacific))

(defpope party-name [party]
  [:div.header--party-name
   [:h1.event-title (:name party)
    (if-not (blank? (:sponsor_name party))
      [:span " by "
       (if (blank? (:sponsor_url party))
         (:sponsor_name party)
         [:a.event-title__link.tint {:href (:sponsor_url party)} (:sponsor_name party)])])]])

(defn date [party]
  (let [date (party/start party)]
    (str (ft/unparse date-formatter date)
         (inf/ordinalize (t/day date)))))

(defn meta-time [party]
  [:div.meta-time
   [:span.event__date (date party)]
   [:div.time
    [:div.event__start
     [:span.title "Begins "]
     [:span.hour (ft/unparse time-formatter (party/start party))]]
    [:div.event__end
     [:span.title "Ends "]
     [:span.hour (ft/unparse time-formatter (party/end party))]]]])

(defn event-address [party]
  [:div.module-location
   [:span.address
    (:location party) [:br]
    (:street_address party) [:br]
    (if (nil? (:city party)) "San Francisco, CA" (:city party))]])

(defn includes-type [type party]
  (boolean (some #(= type %) (:types party))))

(defn party-types [party]
  [:ul.list-details
   (if (includes-type "party" party)
     [:li "Party"])
   (if (includes-type "meetup" party)
     [:li "Meetup"])
   (if (includes-type "presentation" party)
     [:li "Presentation"])
   (if (includes-type "outdoors" party)
     [:li "Outdoors"])
   (if (:18+ (:meta party))
     [:li "Must be over 18 to attend."])
   (if (:21+ (:meta party))
     [:li "Must be over 21 to attend."])
   (if (:booze (:meta party))
     [:li "Alcohol Available"])
   (if (:food (:meta party))
     [:li "Food Available"])
   (if (:ticket (:meta party))
     [:li "Ticket/RSVP Required"]
     [:li "Open to All"])])

(defpope metadata [party]
   (meta-time party))

(defpope description [party]
  (list
   [:h2.description__header "Event Description"]
   (:description party)))

(defpope event-contact [party]
  [:div.event-contact
   [:h3 "Contact"]
   (if-not (blank? (:event_url party))
     [:a.website.tint {:href (:event_url party)} icons/website "Website"])
   (if-not (blank? (:twitter_handle party))
     [:a.twitter {:href (str "https://twitter.com/" (:twitter_handle party))}
      icons/twitter
      (:twitter_handle party)])])

(defpope event-info [party]
  [:div.event-info
   [:h3 "Other Details"]
   (party-types party)])

(defpope metadata-location [party]
  [:div.event-location
   (if-not (blank? (:street_address party))
     [:div.event-map
      [:a {:href (map/apple-maps-url party)}
       [:img.map__image {:alt (str "Map to " (:location party)) :src (map/url party)}]]
       (event-address party)
       [:div.map-actions
        [:a.map__link.tint {:href (map/apple-maps-url party)}
         [:span "View in"] " Apple Maps"]
        [:p.map__source "Map by Mapbox"]]])])

(defpope nav-links [party]
  [:div.nav-links
   (if (not (nil? (:prev party)))
     [:a.nav-link__prev {:href (str "/parties/" (:slug (:prev party)))} icons/arrow-left (str (:name (:prev party)))])
   (if (not (nil? (:next party)))
     [:a.nav-link__next {:href (str "/parties/" (:slug (:next party)))} (str (:name (:next party))) icons/arrow-right])])

(defn page [party]
  (with-party party
    (list
     [:head shared-head
      (twitter/card party)
      [:title (str (:name party) " | WWDC Parties 2016")]]
     [:body {:id (colors/tint party)}
      nav/header
      [:article {:role "main" }
       (party-name)
       [:div.section-metadata
        (metadata)
        (metadata-location)]
       [:div.event-description
        (description)
        (event-contact)
        (event-info)]
       (nav-links)]
      footer/footer
      scripts/scripts])))
