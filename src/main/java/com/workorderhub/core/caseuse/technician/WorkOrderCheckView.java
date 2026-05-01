package com.workorderhub.core.caseuse.technician;

import com.workorderhub.infrastructure.models.UsedSparePartModel;

import java.time.LocalDate;
import java.util.List;

/**
 * Vista del caso de uso de revisión de orden de trabajo por parte del técnico.
 * Define los métodos que la capa de presentación debe implementar para mostrar
 * la información completa de una orden de trabajo.
 */
public interface WorkOrderCheckView {

    /**
     * Establece las fechas del período de vigencia de la orden de trabajo.
     *
     * @param startDate fecha de inicio del período.
     * @param endDate   fecha de fin del período.
     */
    void setPeriodDate(LocalDate startDate, LocalDate endDate);

    /**
     * Muestra los datos generales de la orden de trabajo.
     *
     * @param description descripción o título de la orden de trabajo.
     * @param status      estado actual de la orden (ej. "Pendiente", "En progreso").
     * @param categories  lista de categorías o tipos de trabajo asociados.
     */
    void setWorkOrderData(String description, String status, List<String> categories);

    /**
     * Muestra la información del elemento de planta asociado a la orden.
     *
     * @param tag         identificador/etiqueta única del elemento de planta.
     * @param location    ubicación física del elemento dentro de la planta.
     * @param description descripción del elemento de planta.
     */
    void setPlantElement(String tag, String location, String description);

    /**
     * Muestra la información del responsable (holder) de la orden de trabajo.
     *
     * @param name    nombre completo del responsable.
     * @param contact información de contacto del responsable.
     */
    void setHolderInfo(String name, String contact);

    /**
     * Muestra la información del permiso de trabajo (Work Permit) asociado.
     *
     * @param description       descripción del permiso de trabajo.
     * @param safetyLock        identificador del dispositivo de bloqueo de seguridad.
     * @param lotoProcedureName nombre del procedimiento LOTO (Lockout/Tagout).
     * @param lotoProcedureCode código del procedimiento LOTO.
     */
    void setWorkPermitInfo(String description, String safetyLock, String lotoProcedureName, String lotoProcedureCode);

    /**
     * Muestra la información del procedimiento de trabajo a ejecutar.
     *
     * @param name nombre del procedimiento de trabajo.
     * @param code código identificador del procedimiento.
     */
    void setWorkProcedureInfo(String name, String code);

    /**
     * Muestra la lista de participantes asignados a la orden de trabajo.
     *
     * @param participants lista con los nombres de los participantes.
     */
    void setParticipants(List<String> participants);

    /**
     * Carga los ítems de repuestos utilizados en la tabla de la vista.
     *
     * @param sparePartList lista de modelos de repuestos usados ({@link UsedSparePartModel}).
     */
    void setSparePartTableItems(List<UsedSparePartModel> sparePartList);
}
