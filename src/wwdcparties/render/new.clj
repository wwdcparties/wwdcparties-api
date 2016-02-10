(ns wwdcparties.render.new
  (:require [hiccup.form :refer :all]
            [ring.middleware.anti-forgery :refer :all]
            [ring.util.anti-forgery :refer :all]
            [wwdcparties.render.nav :as nav]
            [wwdcparties.render.footer :as footer]
            [wwdcparties.render.icons :as icons]
            [wwdcparties.render.scripts :as scripts]
            [wwdcparties.render.core :refer :all]))

(defpope basic-info [party]
  [:fieldset
   [:legend "Location"]
   (label "choice_party_name" "Party Name")
   (text-field "choice_party_name" (:name party))
   (label "choice_venue" "Venue")
   (text-field {:oninput "loadSuggestions()"} "choice_venue" (:address party))
   [:ul#locations]
   (label "description" "Description")
   [:textarea {:id "description" }]])

(defpope logistics [party]
  [:fieldset
   [:legend "When"]
    [:ul.checkbox
     (for [day ["Sunday" "Monday" "Tuesday" "Wednesday" "Thursday" "Friday" "Saturday"]]
          (list  [:li
                  [:input {:type "checkbox" :id day :value day :name day}]
                  [:label {:for day} day]]))]
     (label "choice_time_start" "Starts")
     (text-field {:type :time} "choice_time_start" (:time party))
     (label "choice_time_end" "Ends")
     (text-field {:type :time} "choice_time_end" (:time party))])

(defpope contact [party]
  [:fieldset
   [:legend "Contact"]
   [:p "Adding a website or Twitter account helps inform attendees of any last minute changes to the event."]
   [:button.add icons/plus "Sponsor"]
   [:button.add icons/plus "Website"]
   [:button.add icons/plus "Twitter"]])

(defpope party-type [party]
  [:fieldset
   [:legend "What to expect"]
    [:ul.checkbox
     (for [party-type ["Outdoor Event" "Party" "Option 3" "Option 4"]]
          (list  [:li
                  [:input {:type "checkbox" :id party-type :value party-type :name party-type}]
                  [:label {:for party-type} party-type]]))]])


(defpope page [party]
  (list
   [:head shared-head]
   [:body.submit-event
    nav/header
    (with-party party
      [:div.container
       [:h1 "Add an event"]
       (form-to [:post "/new"]
                (basic-info)
                (logistics)
                (contact)
                (party-type)
                [:button {:type "submit"} "Submit Party"]
                (anti-forgery-field))])
    footer/footer
    scripts/scripts]))
