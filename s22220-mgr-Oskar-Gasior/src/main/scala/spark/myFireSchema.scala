package spark

import org.apache.spark.sql.types.{BooleanType, FloatType, IntegerType, StringType}

case class myFireSchema(
                         CallNumber: IntegerType,
                         UnitID: StringType,
                         IncidentNumber: IntegerType,
                         CallType: StringType,
                         CallDate: StringType,
                         WatchDate: StringType,
                         CallFinalDisposition: StringType,
                         AvailableDtTm: StringType,
                         Address: StringType,
                         City: StringType,
                         Zipcode: IntegerType,
                         Battalion: StringType,
                         StationArea: StringType,
                         Box: StringType,
                         OriginalPriority: StringType,
                         Priority: StringType,
                         FinalPriority: IntegerType,
                         ALSUnit: BooleanType,
                         CallTypeGroup: StringType,
                         NumAlarms: IntegerType,
                         UnitType: StringType,
                         UnitSequenceInCallDispatch: IntegerType,
                         FirePreventionDistrict: StringType,
                         SupervisorDistrict: StringType,
                         Neighborhood: StringType,
                         Location: StringType,
                         RowID: StringType,
                         Delay: FloatType
                       )
