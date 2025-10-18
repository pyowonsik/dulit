package com.example.dulit.feature.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dulit.feature.home.domain.model.Anniversary
import com.example.dulit.feature.home.domain.model.Plan

@Entity(tableName = "plans")
data class PlanEntity(
    @PrimaryKey
    val id: Int,
    val topic: String,
    val location: String,
    val time: String,
    val createdAt: String,
    val updatedAt: String
) {
    fun toDomain() = Plan(
        id = id,
        topic = topic,
        location = location,
        time = time,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    companion object {
        /**
         * Domain Model → Entity 변환 (Room 저장용)
         */
        fun fromDomain(plan: Plan) = PlanEntity(
            id = plan.id,
            topic = plan.topic,
            location = plan.location,
            time = plan.time,
            createdAt = plan.createdAt,
            updatedAt = plan.updatedAt
        )
    }
}
