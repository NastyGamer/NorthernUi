package northern.ui

import java.time.LocalDate

data class Servers(val creationDate: LocalDate, val servers: Map<String, List<Server>>)