<?php
namespace OCA\Dashboard;

use \OCA\Dashboard\App\Dashboard;

$application = new Dashboard();
$application->registerRoutes($this, array(
    'routes' => array(
        // WEB PAGE
        array(
            'name' => 'page#index',
            'url' => '/',
            'verb' => 'GET',
        ),
        // STATS API
        array(
            'name' => 'api_stats#stats',
            'url' => '/api/1.0/stats.{format}',
            'defaults' => array('format' => 'json'),
            'verb' => 'GET',
        ),
        array(
            'name' => 'api_stats#index',
            'url' => '/api/1.0/index.{format}',
            'defaults' => array('format' => 'json'),
            'verb' => 'GET',
        ),
        array(
            'name' => 'api_stats#history_stats',
            'url' => '/api/1.0/history_stats/{format}/{gid}/{dataType}/{range}/{wanthumanreadable}',
            'defaults' => array('format' => 'json', 'gid' => 'none', 'dataType' => 'all', 'range' => 30, 'wanthumanreadable' => 1),
            'verb' => 'GET',
        ),
        // GROUPS API
        array(
            'name' => 'api_groups#is_groups_enabled',
            'url' => '/api/1.0/is_groups_enabled',
            'verb' => 'GET',
        ),
        array(
            'name' => 'api_groups#groups',
            'url' => '/api/1.0/groups/{search}',
            'default' => array('search' => ''),
            'verb' => 'GET',
        ),
        array(
            'name' => 'api_groups#stats_enabled_groups',
            'url' => '/api/1.0/stats_enabled_groups',
            'verb' => 'GET',
        ),
        // CORS
        array(
            'name' => 'api_stats#preflighted_cors',
            'url' => '/api/1.0/stats/{path}',
            'verb' => 'OPTIONS',
            'requirements' => array('path' => '.+'),
        ),
    ),
));
