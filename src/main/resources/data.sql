-- Timezones (standard UTC offset in minutes; observes_dst = zone ever uses DST)
INSERT IGNORE INTO timezones (id, zone_id, display_name, region, utc_offset_minutes, observes_dst) VALUES
(1,  'UTC',                  'Coordinated Universal Time',    'Global',        0,    false),
(2,  'America/New_York',     'Eastern Time',                  'North America', -300, true),
(3,  'America/Chicago',      'Central Time',                  'North America', -360, true),
(4,  'America/Denver',       'Mountain Time',                 'North America', -420, true),
(5,  'America/Los_Angeles',  'Pacific Time',                  'North America', -480, true),
(6,  'America/Sao_Paulo',    'Brasilia Time',                 'South America', -180, false),
(7,  'Europe/London',        'Greenwich Mean Time',           'Europe',        0,    true),
(8,  'Europe/Paris',         'Central European Time',         'Europe',        60,   true),
(9,  'Europe/Berlin',        'Central European Time',         'Europe',        60,   true),
(10, 'Europe/Moscow',        'Moscow Time',                   'Europe',        180,  false),
(11, 'Asia/Dubai',           'Gulf Standard Time',            'Middle East',   240,  false),
(12, 'Asia/Kolkata',         'India Standard Time',           'Asia',          330,  false),
(13, 'Asia/Singapore',       'Singapore Time',                'Asia',          480,  false),
(14, 'Asia/Tokyo',           'Japan Standard Time',           'Asia',          540,  false),
(15, 'Asia/Shanghai',        'China Standard Time',           'Asia',          480,  false),
(16, 'Australia/Sydney',     'Australian Eastern Time',       'Australia',     600,  true),
(17, 'Pacific/Auckland',     'New Zealand Standard Time',     'Pacific',       720,  true),
(18, 'Africa/Cairo',         'Eastern European Time',         'Africa',        120,  false),
(19, 'Africa/Johannesburg',  'South Africa Standard Time',    'Africa',        120,  false);

-- Cities
INSERT IGNORE INTO cities (id, name, country_code, country_name, population, timezone_id) VALUES
-- North America / Eastern
(1,  'New York City',    'US', 'United States',      8336817,  2),
(2,  'Washington D.C.',  'US', 'United States',       689545,  2),
(3,  'Boston',           'US', 'United States',       675647,  2),
(4,  'Miami',            'US', 'United States',       454279,  2),
(5,  'Toronto',          'CA', 'Canada',             2731571,  2),
-- North America / Central
(6,  'Chicago',          'US', 'United States',      2696555,  3),
(7,  'Houston',          'US', 'United States',      2304580,  3),
(8,  'Dallas',           'US', 'United States',      1304379,  3),
-- North America / Mountain
(9,  'Denver',           'US', 'United States',       715522,  4),
(10, 'Salt Lake City',   'US', 'United States',       200567,  4),
-- North America / Pacific
(11, 'Los Angeles',      'US', 'United States',      3979576,  5),
(12, 'San Francisco',    'US', 'United States',       873965,  5),
(13, 'Seattle',          'US', 'United States',       737255,  5),
(14, 'Vancouver',        'CA', 'Canada',              631486,  5),
-- South America
(15, 'Sao Paulo',        'BR', 'Brazil',            12325232,  6),
(16, 'Rio de Janeiro',   'BR', 'Brazil',             6747815,  6),
-- Europe / GMT
(17, 'London',           'GB', 'United Kingdom',     8982000,  7),
(18, 'Dublin',           'IE', 'Ireland',            1388000,  7),
-- Europe / CET (Paris zone)
(19, 'Paris',            'FR', 'France',             2161000,  8),
(20, 'Madrid',           'ES', 'Spain',              3223334,  8),
(21, 'Rome',             'IT', 'Italy',              2873000,  8),
(22, 'Amsterdam',        'NL', 'Netherlands',         921402,  8),
-- Europe / CET (Berlin zone)
(23, 'Berlin',           'DE', 'Germany',            3677472,  9),
(24, 'Munich',           'DE', 'Germany',            1484226,  9),
-- Europe / Moscow
(25, 'Moscow',           'RU', 'Russia',            12506468, 10),
(26, 'Saint Petersburg', 'RU', 'Russia',             5601000, 10),
-- Middle East
(27, 'Dubai',            'AE', 'United Arab Emirates', 3331420, 11),
(28, 'Abu Dhabi',        'AE', 'United Arab Emirates', 1483000, 11),
-- Asia / India
(29, 'Mumbai',           'IN', 'India',             20667656, 12),
(30, 'Delhi',            'IN', 'India',             32941000, 12),
(31, 'Bangalore',        'IN', 'India',             12765000, 12),
-- Asia / Singapore
(32, 'Singapore',        'SG', 'Singapore',          5850342, 13),
-- Asia / Japan
(33, 'Tokyo',            'JP', 'Japan',             13960000, 14),
(34, 'Osaka',            'JP', 'Japan',              2691185, 14),
-- Asia / China
(35, 'Shanghai',         'CN', 'China',             24870895, 15),
(36, 'Beijing',          'CN', 'China',             21893095, 15),
-- Australia
(37, 'Sydney',           'AU', 'Australia',          5312000, 16),
(38, 'Melbourne',        'AU', 'Australia',          5078193, 16),
-- Pacific
(39, 'Auckland',         'NZ', 'New Zealand',        1657200, 17),
(40, 'Wellington',       'NZ', 'New Zealand',         418500, 17),
-- Africa
(41, 'Cairo',            'EG', 'Egypt',             21323000, 18),
(42, 'Johannesburg',     'ZA', 'South Africa',       5635127, 19),
(43, 'Cape Town',        'ZA', 'South Africa',       4618000, 19);

-- DST Transitions (year = calendar year in which DST period starts)
-- US zones: 2nd Sunday of March -> 1st Sunday of November
INSERT IGNORE INTO dst_transitions (id, timezone_id, year, dst_start, dst_end) VALUES
(1,  2, 2024, '2024-03-10 02:00:00', '2024-11-03 02:00:00'),
(2,  2, 2025, '2025-03-09 02:00:00', '2025-11-02 02:00:00'),
(3,  3, 2024, '2024-03-10 02:00:00', '2024-11-03 02:00:00'),
(4,  3, 2025, '2025-03-09 02:00:00', '2025-11-02 02:00:00'),
(5,  4, 2024, '2024-03-10 02:00:00', '2024-11-03 02:00:00'),
(6,  4, 2025, '2025-03-09 02:00:00', '2025-11-02 02:00:00'),
(7,  5, 2024, '2024-03-10 02:00:00', '2024-11-03 02:00:00'),
(8,  5, 2025, '2025-03-09 02:00:00', '2025-11-02 02:00:00'),
-- Europe zones: last Sunday of March -> last Sunday of October
(9,  7, 2024, '2024-03-31 01:00:00', '2024-10-27 02:00:00'),
(10, 7, 2025, '2025-03-30 01:00:00', '2025-10-26 02:00:00'),
(11, 8, 2024, '2024-03-31 02:00:00', '2024-10-27 03:00:00'),
(12, 8, 2025, '2025-03-30 02:00:00', '2025-10-26 03:00:00'),
(13, 9, 2024, '2024-03-31 02:00:00', '2024-10-27 03:00:00'),
(14, 9, 2025, '2025-03-30 02:00:00', '2025-10-26 03:00:00'),
-- Australia/Sydney: 1st Sunday of October -> 1st Sunday of April (spans calendar years)
(15, 16, 2023, '2023-10-01 02:00:00', '2024-04-07 03:00:00'),
(16, 16, 2024, '2024-10-06 02:00:00', '2025-04-06 03:00:00'),
-- Pacific/Auckland: last Sunday of September -> 1st Sunday of April (spans calendar years)
(17, 17, 2023, '2023-09-24 02:00:00', '2024-04-07 03:00:00'),
(18, 17, 2024, '2024-09-29 02:00:00', '2025-04-06 03:00:00');
