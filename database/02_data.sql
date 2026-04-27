INSERT INTO status(order_status)
    VALUES
        ('Open'),
        ('On going'),
        ('Closed');

INSERT INTO category(category_name, category_description)
    VALUES
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
        ('Centrifugal Pump', 'EQ001', 'Grundfos CM3-5 high-pressure centrifugal pump, 50 Hz, 220V, 2.2 kW', 12, 1),
        ('Air Compressor', 'EQ002', 'Ingersoll Rand UP5-7.5TAS rotary screw air compressor, 7.5 HP', 8, 1),
        ('Circuit Breaker', 'EL001', 'Schneider Electric NSX100F, 100A circuit breaker, 3-pole, 415V', 25, 2),
        ('Control Relay', 'EL002', 'Siemens 3RT2026-1AK60 contactor relay, 24V DC coil, 3 NO', 60, 2),
        ('Transformer', 'EL003', 'ABB dry-type transformer, 11kV/400V, 500 kVA', 5, 2),
        ('Bearing', 'ME001', 'SKF 6305 deep groove ball bearing, 25 mm bore, 62 mm OD', 150, 3),
        ('Valve Actuator', 'ME002', 'Rotork IQ3 electric valve actuator, 240V AC, torque 150 Nm', 10, 3),
        ('Pressure Gauge', 'IN001', 'WIKA 213.53 stainless steel pressure gauge, 0-10 bar, 63mm dial', 30, 4),
        ('Thermocouple', 'IN002', 'Omega K-type thermocouple, stainless steel, 300 mm probe length, 0-400°C', 50, 4),
        ('Flow Meter', 'IN003', 'Yokogawa AXF electromagnetic flowmeter, DN100, 4-20 mA output', 6, 4);

INSERT INTO plant_element (element_tag, element_description, element_location)
    VALUES
        ('FV-101', 'Flow Control Valve', 'Reactor Area - Unit 1'),
        ('PT-202', 'Pressure Transmitter, Range 0-100 psi', 'Compressor Room - Unit 3'),
        ('LV-303', 'Level Control Valve, 3-inch, Pneumatic Actuated', 'Storage Tank Area - Unit 4'),
        ('TT-404', 'Temperature Transmitter, Range -50 to 300°C', 'Distillation Column - Unit 2'),
        ('XV-505', 'Emergency Shut-off Valve, Remote Actuated', 'Boiler Room - Unit 5'),
        ('FT-606', 'Flow Transmitter, Magnetic Flow Range 0-500 gpm', 'Cooling Tower - Unit 1'),
        ('CV-707', 'Control Valve', 'Heat Exchanger - Unit 2'),
        ('PV-808', 'Pressure Relief Valve, Set Pressure 120 psi', 'Steam Generation Area - Unit 3'),
        ('IT-909', 'Inductive Transmitter, Range 0-1000 rpm', 'Pump Station - Unit 4'),
        ('HV-010', 'Hand Valve, 3-inch', 'Chemical Storage - Unit 5');

INSERT INTO employee_role (role_name)
    VALUES
        ('Manager'),
        ('Supervisor'),
        ('Technician');

INSERT INTO user_credentials (user_name, user_password)
    VALUES
        ('plantmanager', 'jsmith123!'),
        ('emilydavis', 'edavis024!'),
        ('michaeljhonson', 'mjhonson158!');

INSERT INTO employee (employee_name, phone_number, company_email, role_id, user_credentials_id)
    VALUES
        ('John Smith', '555-1234', 'john.smith@chemplant.com', 1, 1),
        ('Emily Davis', '555-2345', 'emily.davis@chemplant.com', 1, 2),
        ('Michael Johnson', '555-3456', 'michael.johnson@chemplant.com', 1, 3),
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
        ('WP-001', 'Filter Replacement Procedure'),
        ('WP-002', 'Pipeline Cleaning Procedure'),
        ('WP-003', 'Heat Exchanger Maintenance Procedure'),
        ('WP-004', 'Compressor Lubrication Procedure'),
        ('WP-005', 'Pump Alignment and Balancing Procedure'),
        ('WP-006', 'Distillation Column Inspection'),
        ('WP-007', 'Boiler Water Treatment Procedure'),
        ('WP-008', 'Cooling Tower Disinfection Procedure'),
        ('WP-009', 'Valve Seal Replacement Procedure'),
        ('WP-010', 'Condenser Tube Cleaning Procedure');

INSERT INTO loto_procedure (loto_procedure_code, loto_procedure_name)
    VALUES
        ('LP-001', 'Pump Lockout Procedure'),
        ('LP-002', 'Blower Lockout Procedure'),
        ('LP-003', 'Electrical Panel Lockout Procedure'),
        ('LP-004', 'Air Compressor Lockout Procedure'),
        ('LP-005', 'Conveyor System Lockout Procedure'),
        ('LP-006', 'Heat Exchanger Lockout Procedure'),
        ('LP-007', 'Reactor Mixer Lockout Procedure'),
        ('LP-008', 'Cooling Tower Fan Lockout Procedure'),
        ('LP-009', 'Steam Valve Lockout Procedure'),
        ('LP-010', 'Boiler Control Panel Lockout Procedure');