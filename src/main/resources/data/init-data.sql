# INSERT IGNORE INTO users (username, created_on, email, enabled, first_name, gender, last_name, last_updated_on, password, phone, sign_in_provider, social_id, address_id, profile_picture_id)
# VALUES ('nikson', now(), 'admin2@ufril.com', '1', 'Nikson', NULL, 'nikson', now(),
#                   '$2a$10$mewU.WzHRdrxv4WT7IANyeb/pElS7mfdlZnCdWNHpYBru/IQnkNG.', NULL, NULL, NULL, NULL, NULL);


-- clean table
# TRUNCATE TABLE ADDRESSES;
# -- insert test data
# INSERT INTO addresses (id, street_address, city, latitude, longitude) VALUES
#   (1, "Test data 1", "CA", 37.3860517, -122.0838511),
#   (2, "Test data 2", "CA", 37.3860520, -122.0838520),
#   (3, "Test data 3", "CA", 37.3860525, -122.0838525);


-- set @lat=37.3860517;
-- set @lng=-122.0838511;

-- SELECT latitude, longitude, (
--    POW(69.1 * (latitude - @lat), 2) + POW(69.1 * (@lng - longitude) * COS(latitude / 57.3), 2))
--	AS distance FROM addresses HAVING distance < 25 ORDER BY distance;


-- clean space table
# TRUNCATE TABLE spaces;

-- insert data in spaces and address table
# INSERT INTO spaces (accommodate, available, check_in_time, check_out_time, created_on, enabled,
#                     last_updated_on, number_of_bedroom, property_type, room_type, address_id, space_owner_username)
# VALUES (1, 1, now(), now(), now(), 1, now(), 1, 'House', 'Bed', 1, 'admin');

# INSERT INTO spaces (accommodate, available, check_in_time, check_out_time, created_on, enabled,
#                     last_updated_on, number_of_bedroom, property_type, room_type, address_id, space_owner_username)
# VALUES (1, 1, now(), now(), now(), 1, now(), 1, 'House', 'Bed', 2, 'admin');

-- vehicles
-- TRUNCATE TABLE VEHICLES;

# INSERT IGNORE INTO vehicles (id, available, color, description, make , model , seats , type , year, vehicle_owner_username )
# VALUES (1, 1, 'Black', 'Just a car', 'Unkown', '1', 1, 'CAR', '2015', 'admin');


-- clean table
# TRUNCATE TABLE rides;
#
# INSERT IGNORE INTO rides
# (id, final_price, create_ride_as, created_on, description, destination_address, destination_latitude,
#  destination_longitude, drop_off_time, last_updated_on, number_of_guest, pick_off_time,
#  source_address, source_latitude, source_longitude, ride_requester_username, ride_status, vehicle_type)
# VALUES (1, 1, 'consumer', now(), 'Just a ride', '685 Durham Ridge Dr NW, Lilburn, GA 30047, USA', 33.89679900000001, -84.16108299999999, now(), now(), 1, now(),
#         '6867 Magnolia Park Dr, Norcross, GA 30093', 33.900694, -84.22617, 'admin', 'REQUESTED', 'CAR');
#

-- clean table tasks
# TRUNCATE TABLE tasks;

# INSERT IGNORE INTO tasks
# (id, create_task_as, created_on, description, last_updated_on, task_date_time, task_size, tasker_bring, address_id, task_category_id, task_requester_username)
# VALUES (1, 'admin', now(), 'Test Task', now(), now(), NULL, NULL, 1, 1, 'admin');

