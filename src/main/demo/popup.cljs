(ns demo.popup)

(defn set-alarm [event]
  (js/console.log "set-alarm" event)

  (let [minutes (js/parseFloat (-> event .-target .-value))]
    (js/chrome.action.setBadgeText #js {:text "ON"})
    (js/chrome.alarms.create #js {:delayInMinutes minutes})
    (js/chrome.storage.sync.set #js {:minutes minutes})
    (js/window.close)

    (js/console.log "set-alarm done")
    ))

(defn clear-alarm [event]
  (js/console.log "clear-alarm" event)

  (js/chrome.action.setBadgeText #js {:text ""})
  (js/chrome.alarms.clearAll)
  (js/window.close)

  (js/console.log "clear-alarm done")
  )

(defn init []
  (doseq [id ["sampleMinute" "min15" "min30"]]
    (-> (js/document.getElementById id)
        (.addEventListener "click" set-alarm)))
  (-> (js/document.getElementById "cancelAlarm")
      (.addEventListener "click" clear-alarm))

  (js/console.log "popup init done"))