(ns wwdcparties.party)

(defn sanitized [party]
	(select-keys party
		[:name :slug :start_time :end_time
		:description :shortcode :event_url
		:street_address :location :twitter
		:sponsor_url :sponsor_name
		:types :meta :excerpt]))
