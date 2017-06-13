<?php


use OCA\Dashboard\App\Dashboard;

$app = new Dashboard;
$c = $app->getContainer();
$statsTaskService = $c->query('StatsTaskService');
$loggerService = $c->query('LoggerService');

$application->add(new OCA\Dashboard\Command\Populate);
$application->add(new OCA\Dashboard\Command\Stats($statsTaskService, $loggerService));
