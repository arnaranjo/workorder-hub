INSERT INTO status(order_status)
    VALUES
        ('Open'),
        ('On going'),
        ('Closed');

INSERT INTO category(category_name, category_description)
    VALUES
        ('Standard', 'Use a standard work permit for routine maintenance and non-hazardous activities.'),
        ('Cold work', 'Use a cold work permit if the work will not produce sufficient heat or energy to ignite flammable materials.'),
        ('Hot work', 'Use a hot work permit for all cutting, welding, soldering and brazing activities.'),
        ('Hoisting', 'Use a hoisting permit if the work required hoisting and lifting operations greater than 1 meter.'),
        ('Confined space', 'Use a confined space permit if the minimum conditions of access, ventilation and work space are not met.');

INSERT INTO spare_part_category(category_name)
    VALUES
        ('Equipment'),
        ('Electrical'),
        ('Mechanical'),
        ('Instrument');

INSERT INTO spare_part (spare_part_name, spare_part_number, spare_part_description, spare_part_stock, spare_part_category_id)
    VALUES
        ('Centrifugal Pump Cartridge', 'EQ001', 'Cartridge assembly for centrifugal feed pump P-101, 2.2 kW, 220 V, 50 Hz', 6, 1),
        ('Air Compressor Service Kit', 'EQ002', 'Service kit for screw air compressor C-201 including filters and separator', 10, 1),
        ('Molded Case Circuit Breaker', 'EL001', '3-pole 100 A circuit breaker for MCC bucket, 415 V', 20, 2),
        ('Contactor Relay 24VDC', 'EL002', 'Control relay for motor starter panel, coil 24 VDC, 3 NO contacts', 40, 2),
        ('Control Transformer', 'EL003', 'Dry-type control transformer 480 VAC to 120 VAC, 1.5 kVA', 8, 2),
        ('Pump Bearing Set', 'ME001', 'Bearing set for process pump shaft, 25 mm bore, sealed type', 80, 3),
        ('Electric Valve Actuator', 'ME002', 'Quarter-turn electric actuator for isolation valves, 240 VAC, 150 Nm', 12, 3),
        ('Pressure Gauge 0-10 bar', 'IN001', 'Stainless steel pressure gauge, 63 mm dial, process connection 1/4 in NPT', 25, 4),
        ('K-Type Thermocouple', 'IN002', 'Mineral insulated thermocouple, 300 mm probe, operating range 0 to 400 deg C', 35, 4),
        ('Magnetic Flow Transmitter', 'IN003', 'Flow transmitter for DN100 magnetic meter, 4-20 mA output with HART', 10, 4);

INSERT INTO plant_element (element_tag, element_description, element_location)
    VALUES
        ('P-101', 'Reactor feed centrifugal pump, 2.2 kW process service', 'Reactor Area - Unit 1'),
        ('C-201', 'Plant air compressor package, 7.5 HP screw type', 'Utilities Building - Unit 1'),
        ('T-301', 'Storage tank level control station with 3 in control valve', 'Tank Farm - Unit 3'),
        ('TT-401', 'Distillation column top temperature transmitter, range 0 to 300 deg C', 'Distillation Area - Unit 2'),
        ('XV-501', 'Emergency isolation valve with remote electric actuator', 'Boiler House - Unit 4'),
        ('FT-601', 'Cooling water flow transmitter for main header, range 0 to 500 gpm', 'Cooling Tower - Unit 5'),
        ('E-701', 'Shell and tube heat exchanger for reactor effluent cooling', 'Process Area - Unit 2'),
        ('PSV-801', 'Steam header safety relief valve set at 120 psi', 'Steam System - Unit 4'),
        ('M-901', 'Agitator drive motor with speed feedback transmitter', 'Reactor Area - Unit 1'),
        ('HV-011', 'Manual isolation valve, 3 in carbon steel body', 'Chemical Storage - Unit 3');

INSERT INTO employee_role (role_name)
    VALUES
        ('Manager'),
        ('Supervisor'),
        ('Technician');

INSERT INTO user_credentials (user_name, user_password)
    VALUES
        ('plantmanager', 'test1!'),
        ('emilydavis', 'test2!'),
        ('michaeljhonson', 'test3!');

INSERT INTO employee (employee_name, phone_number, company_email, role_id, user_credentials_id)
    VALUES
        ('John Smith', '555-1234', 'john.smith@chemplant.com', 1, 1),
        ('Emily Davis', '555-2345', 'emily.davis@chemplant.com', 2, 2),
        ('Michael Johnson', '555-3456', 'michael.johnson@chemplant.com', 3, 3),
        ('Sophia Lee', '555-4567', 'sophia.lee@chemplant.com', 2, NULL),
        ('Olivia Green', '555-6789', 'olivia.green@chemplant.com', 3, NULL),
        ('David Brown', '555-5678', 'david.brown@chemplant.com', 3, NULL),
        ('James Wilson', '555-7890', 'james.wilson@chemplant.com', 3, NULL),
        ('Amelia White', '555-8901', 'amelia.white@chemplant.com', 3, NULL),
        ('James McDonall', '555-6523', 'james.mcdonall@chemplant.com', 3, NULL),
        ('Max Power', '555-8821', 'max.power@chemplant.com', 1, NULL),
        ('Laura Stevens', '555-0193', 'laura.stevens@chemplant.com', 2, NULL),
        ('Michael O’Donnell', '555-0284', 'michael.odonnell@chemplant.com', 3, NULL);

INSERT INTO work_procedure (work_procedure_code, work_procedure_name)
    VALUES
        ('WP-001', 'P-101 Feed Pump Preventive Maintenance'),
        ('WP-002', 'C-201 Air Compressor Lubrication and Filter Change'),
        ('WP-003', 'T-301 Tank Level Control Loop Calibration'),
        ('WP-004', 'TT-401 Distillation Temperature Transmitter Calibration'),
        ('WP-005', 'XV-501 Emergency Isolation Valve Functional Test'),
        ('WP-006', 'FT-601 Cooling Water Flow Transmitter Verification'),
        ('WP-007', 'E-701 Heat Exchanger Mechanical Cleaning'),
        ('WP-008', 'PSV-801 Steam Relief Valve Inspection and Setpoint Check'),
        ('WP-009', 'M-901 Agitator Motor Alignment and Coupling Check'),
        ('WP-010', 'HV-011 Manual Isolation Valve Packing Replacement');

INSERT INTO loto_procedure (loto_procedure_code, loto_procedure_name)
    VALUES
        ('LP-001', 'LOTO for P-101 Feed Pump and Associated MCC Feeder'),
        ('LP-002', 'LOTO for C-201 Air Compressor and Receiver Isolation'),
        ('LP-003', 'LOTO for T-301 Tank Inlet and Outlet Isolation Valves'),
        ('LP-004', 'LOTO for TT-401 Instrument Loop and Junction Box Power'),
        ('LP-005', 'LOTO for XV-501 Emergency Valve Electrical Actuator'),
        ('LP-006', 'LOTO for FT-601 Flow Meter Impulse and Signal Isolation'),
        ('LP-007', 'LOTO for E-701 Heat Exchanger Process and Utility Lines'),
        ('LP-008', 'LOTO for PSV-801 Upstream Steam Block Valve'),
        ('LP-009', 'LOTO for M-901 Agitator Motor and Mechanical Energy'),
        ('LP-010', 'LOTO for HV-011 Chemical Storage Isolation Boundary');
