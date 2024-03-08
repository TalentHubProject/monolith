package org.talenthub

import jakarta.ws.rs.ApplicationPath
import jakarta.ws.rs.core.Application

@ApplicationPath("/api/v1")
class ApiApplication
    : Application()